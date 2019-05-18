package com.farmershao.stock.service;

import com.farmershao.stock.config.CommonConfig;
import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.constant.SmsEnum;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.persistence.mapper.SmsSendLogMapper;
import com.farmershao.stock.persistence.model.SmsSendLog;
import com.farmershao.stock.util.AuthCodeUtil;
import com.farmershao.stock.util.HttpUtil;
import com.farmershao.stock.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信发送服务 东联中讯
 *
 * @author Shao Yu
 * @since 2018/5/7 14:13
 **/
@Service
public class SmsMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsMessageService.class);
    private static final String DLZX_SEND_MESSAGE = "http://api.zosms.com:6000/utf8/web_api/SendSMS.aspx";

    @Resource
    private CommonConfig commonConfig;
    @Resource
    private SmsSendLogMapper smsSendLogMapper;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 通过短信服务商发送短信
     * @param mobilePhone   手机号（未加密）
     * @param ip            client Ip
     * @param type          消息类型
     * @param accessApp     app id
     * @return 短信发送结果
     */
    public ResponseDto sendSMS(String mobilePhone, String ip, Integer type, String accessApp){
        ResponseDto result = new ResponseDto(ResponseCodeEnum.SUCCESS);
        try {
            //1. 校验手机号格式
            if(!checkMobileFormat(mobilePhone)){
                result.setCode(ResponseCodeEnum.FAILURE.getCode());
                result.setMsg("手机格式错误");
            }

            //2. 防刷措施
            ResponseDto defendVicious = defendVicious(ip);
            if (ResponseCodeEnum.FAILURE.getCode() == (defendVicious.getCode())) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("ip请求手机校验码过于频繁，ip：{},mobile:{}", ip, mobilePhone);
                }
                return defendVicious;
            }
            //2-2 : 短信验证码60S防刷
//            Boolean flag = (Boolean)CacheHelper.get(CacheType.ONE_MINUTE_STORE.getValue(), CacheHelper.buildKey(CacheConstant.SMS_SIXTY_SECONDS + mobilePhone));
//            if(flag != null) {
//                return new DefaultResult(new ResultCode(ReturnCode.MESSAGE_LIMIT_60S, "60S后在请求发送短信验证码"));
//            } else {
//                CacheHelper.put(CacheType.ONE_MINUTE_STORE.getValue(), CacheHelper.buildKey(CacheConstant.SMS_SIXTY_SECONDS + mobilePhone), true);
//            }
            //请求东联中讯
            sendToDlzx(mobilePhone, type,accessApp);
            redisUtil.deleteKey(CacheKeyEnum.SMS_IMGCODE_LOGIN + mobilePhone);
        } catch (Exception e) {
            LOG.error("东联中讯发送短信验证码异常 mobile:" + mobilePhone, e);
        }
        return result;
    }

    private void sendToDlzx(String mobilePhone, Integer type,String accessApp) {
        //生成4位验证码，并放入缓存
        CacheKeyEnum keyNum;
        String key;
        if (type == 3) {
            keyNum = CacheKeyEnum.ADD_BANK_SMS_CODE;
            key = keyNum + mobilePhone;
        } else {
            keyNum = CacheKeyEnum.LOGIN_SMS_CODE;
            key = keyNum + mobilePhone;
        }
        String authCode = redisUtil.getValue(key, String.class);
        if (StringUtils.isBlank(authCode)) {
            // 没有图片码，生成短信码
            authCode = AuthCodeUtil.getSmsCode(4);
            redisUtil.setValue(key, authCode, keyNum.getExpire());
        }
        Map<String, String> infoParam = new HashMap<>();
        String eid = commonConfig.getEid();
        String uid = commonConfig.getUid();
        String pwdHash = commonConfig.getPwd();
        String appName = commonConfig.getAppName();

        infoParam.put("X_eid", eid);
        infoParam.put("X_uid", uid);
        infoParam.put("X_pwd_hash", pwdHash);
        infoParam.put("X_ac", SmsEnum.DLZXSmsType.NORMAL_MESSAGE.getValue());
        infoParam.put("X_gate_id", "300"); //300 不能变
        infoParam.put("X_target_no", mobilePhone);
        //设置短信模板
        String message = SmsEnum.SmsMessageTypeEnum.getContentByValue(type, authCode, SmsEnum.AppName.getNameByValue(appName));
        infoParam.put("X_memo", message);
        //插入短信发送执行日志
        SmsSendLog smsSendLog = new SmsSendLog();
        smsSendLog.setMobile(mobilePhone);
        smsSendLog.setMessage(message);
        smsSendLog.setMessageType(SmsEnum.SmsMessageTypeEnum.getNameByValue(type));
        smsSendLog.setChannel("东联中讯");
        String doGet = HttpUtil.doGet(DLZX_SEND_MESSAGE, infoParam);
        //doGet 为null ,则再次请求东联
        if (StringUtils.isBlank(doGet)) {
            doGet = HttpUtil.doGet(DLZX_SEND_MESSAGE, infoParam);
        }
        if (StringUtils.isNotBlank(doGet)) {
            smsSendLog.setResultCode(doGet);
            smsSendLog.setResultType(new Integer(doGet) > 0 ? SmsEnum.ResultType.SUCCESS.getValue() : SmsEnum.ResultType.FAIL.getValue());
        } else {
            smsSendLog.setResultType(SmsEnum.ResultType.FAIL.getValue());
        }
        LOG.info("发送短信验证码 mobile:{}, result: {}", mobilePhone, doGet);
        smsSendLogMapper.insert(smsSendLog);

        redisUtil.setValue(CacheKeyEnum.SIGN_NAME_LOGIN_SMS_CODE + mobilePhone , "dlzx", CacheKeyEnum.SIGN_NAME_LOGIN_SMS_CODE.getExpire());
    }

    /**
     * 校验手机号码格式是否正确
     * @param mobile    手机号码
     * @return
     */
    public boolean checkMobileFormat(String mobile) {
        Pattern mobileP = Pattern.compile("^1\\d{10}$");
        Matcher mobileM = mobileP.matcher(mobile);
        return mobileM.matches();
    }

    /**
     * 防止恶意刷新
     * @param ip client ip
     */
    private ResponseDto defendVicious(String ip) {
        Long tenMinCount = getBrushCount(CacheKeyEnum.TEN_MINUTE_COUNT, ip);
        Long oneDayCount = getBrushCount(CacheKeyEnum.ONE_DAY_COUNT, ip);
        return ipRequestCheck(tenMinCount, oneDayCount, ip);
    }

    private Long getBrushCount(CacheKeyEnum key, String ip) {
        Long count = redisUtil.getValue(key.getKey() + ip, Long.class);
        if (count == null) {
            count = 1L;
        } else {
            count ++;
        }
        redisUtil.setValue(key.getKey() + ip, count, key.getExpire());
        return count - 1;
    }

    private ResponseDto ipRequestCheck(Long tenMinCount, Long oneDayCount, String ip){
        tenMinCount = tenMinCount == null ? 0 : tenMinCount;
        oneDayCount = oneDayCount == null ? 0 : oneDayCount;
        ResponseDto result = new ResponseDto(ResponseCodeEnum.SUCCESS);
        try {
            if (tenMinCount >= commonConfig.getSmsTenMinutesMax()) {//十分钟注册次数达到上限
                if(LOG.isDebugEnabled()){
                    LOG.debug("该ip10分钟内申请次数已达到上限{}次，本次操作禁止,ip:{}，10分钟申请次数：{}",
                            commonConfig.getSmsTenMinutesMax(), ip, tenMinCount);
                }
                result.setCode(ResponseCodeEnum.FAILURE.getCode());
                result.setMsg("抱歉，您的注册次数过于频繁，建议您休息一会儿再试！");
            }
            if (oneDayCount >= commonConfig.getSmsOneDayMax()) {//24小时注册次数达到上限
                if(LOG.isDebugEnabled()){
                    LOG.debug("该ip24小时内注册已达到上限{}次，本次操作禁止,ip:{},24小时申请次数：{}",
                            commonConfig.getSmsOneDayMax(), ip, oneDayCount);
                }
                result.setCode(ResponseCodeEnum.FAILURE.getCode());
                result.setMsg("您所使用的IP地址异常，请重新访问。如有疑问，请拨打客服电话。");
            }
        } catch (Exception e) {
            LOG.error("ip检测异常 IP:" + ip, e);
            result.setCode(ResponseCodeEnum.FAILURE.getCode());
            result.setMsg("ip检测异常");
        }
        return result;
    }

}
