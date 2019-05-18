package com.farmershao.stock.trade.utils;


import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.util.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 股票资金缓存
 * @Author shao yu
 * @Since 2018/08/29
 */
@Component
public class StockAccountFundCacheUtil {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 初始化股票账户资金缓存
     * @param account 股票账户
     * @param money   可用资金
     */
    public void initAccountMoney(String account, BigDecimal money) {
        redisUtil.setHash(CacheKeyEnum.ACCOUNT_MONEY.getKey(), account, money, CacheKeyEnum.ACCOUNT_MONEY.getExpire());
    }

    /**
     * 计算股票账户可用资金
     * @param account   股票账号
     * @param passive   使用金额（若买单为负，卖单位正）
     */
    public void calAccountMoney(String account, BigDecimal passive) {
        BigDecimal money = (BigDecimal)redisUtil.getHash(CacheKeyEnum.ACCOUNT_MONEY.getKey(), account);
        if (money != null) {
            money = money.add(passive);
        }
        redisUtil.setHash(CacheKeyEnum.ACCOUNT_MONEY.getKey(), account, money, CacheKeyEnum.ACCOUNT_MONEY.getExpire());
    }

}
