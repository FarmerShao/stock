package com.farmershao.stock.trade.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubUtil {
    private static final Logger logger = LoggerFactory.getLogger(PubUtil.class);

    public static Date getCurrDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取今天的凌晨
     * 
     * @return
     */
    public static Date getCurrDateStart() {
        return getDateStart(getCurrDate());
    }

    /**
     * getCurrDateFormat:按照指定格式获取当前日期
     *
     * @author zhuangjiaju
     * @param format
     * @return
     * @date 2015年11月26日 下午3:00:40
     */
    public static String getCurrDateFormat(String format) {
        return dateFormat(getCurrDate(), format);
    }

    /**
     * 获取当前日期时间戳
     * 
     * @return
     */
    public static String getCurrTimeStamp() {
        return getCurrDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * getCurrDateFormat:按照指定格式获取当前日期
     *
     * @author zhuangjiaju
     * @param format
     * @return
     * @date 2015年11月26日 下午3:00:40
     */
    public static String getCurrDateFormat() {
        return getCurrDateFormat("yyyy-MM-dd");

    }

    /**
     * string2Date:记住所有的日期转换都在这里写，任何格式加在else if 里面 别额外再新增日期转换的公共类
     *
     * @author zhuangjiaju
     * @param date
     * @return
     * @date 2015年11月26日 下午2:41:39
     */
    public static Date str2Date(String date) {
        if (PubUtil.isEmpty(date)) {
            return null;
        }
        try {
            String format;
            if (date.indexOf("-") != -1) {
                if (date.indexOf(":") != -1) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd";
                }
            } else if (date.indexOf("/") != -1) {
                if (date.indexOf(":") != -1) {
                    format = "yyyy/MM/dd HH:mm:ss";
                } else {
                    format = "yyyy/MM/dd";
                }
            } else {
                format = "yyyyMMddHHmmss";
            }
            SimpleDateFormat sd = new SimpleDateFormat(format);
            return sd.parse(date);
        } catch (Exception e) {
            logger.error("日期转换错误,错误的日期格式为：" + date, e);
        }
        return null;
    }

    /**
     * 获取某一天的凌晨
     * 
     * @return
     */
    public static Date getDateStart(Date date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sd.format(date);
        return str2Date(dateStr);
    }

    public static String dateFormat(Date date, String format) {
        String dateStr = "";
        if (date == null || isEmpty(format)) {
            return dateStr;
        }
        try {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            dateStr = sd.format(date);
        } catch (Exception e) {
            logger.error("日期格式转换出错，错误的格式为:" + format, e);
        }
        return dateStr;
    }


    /**
     * 补全数据，类似于 传入1 3 0 输出 001
     * 
     * @param length
     * @return
     */
    public static String leftPad(String str, int length, String add) {
        if (add.length() != 1) {
            return str;
        }
        if (str.length() >= length) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - str.length(); i++) {
            sb.append(add);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 根据元的金额转换成分的金额，如果异常则返回为null
     * 
     * @param money
     * @return
     */
    public static Long getMoneyYuan2Fen(Object money) {
        Long l = null;
        Double d = toDouble(money);
        if (d != null) {
            l = toLong(mul(d, 100));
        }
        return l;
    }

    /**
     * 根据元的金额转换成分的金额，如果异常则返回为Null
     * 
     * @param money
     * @return
     */
    public static Double getMoneyFen2Yuan(Object money) {
        Double rd = null;
        Double d = toDouble(money);
        if (d != null) {
            rd = div(d, 100);
        }
        return rd;
    }

    /**
     * 判断对象问是否为空或者为默认值
     * 
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            String str = toString(obj);
            return ((str == null) || (str.trim().length() == 0));
        } else if (obj instanceof Long) {
            Long l = toLong(obj);
            return ((l == null) || (l == 0));
        } else if (obj instanceof Double) {
            Double d = toDouble(obj);
            return ((d == null) || (d == 0));
        } else if (obj instanceof Integer) {
            Integer i = toInteger(obj);
            return ((i == null) || (i == 0));
        } else if (obj instanceof Byte) {
            Byte b = toByte(obj);
            return ((b == null) || (b == 0));
        } else if (obj instanceof Boolean) {
            Boolean b = toBoolean(obj);
            return ((b == null) || (b == Boolean.FALSE));
        } else if (obj instanceof List) {
            List list = (List) obj;
            return ((list == null) || (list.size() == 0));
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return ((map == null) || (map.size() == 0));
        } else {
            return obj == null;
        }
    }

    /**
     * 判断对象问是否为空或者为默认值
     * 
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 在LIKE语句旁边加上两个百分号
     * 
     * @param str
     * @return
     */
    public static String addSqlLike(String str) {
        String sql = "";
        if (isNotEmpty(str)) {
            sql = "%" + str + "%";
        } else {
            sql = "%";
        }
        return sql;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * isNotEmptyAndMatches:判断一个字段不为空，且符合某个正则表达式
     *
     * @author zhuangjiaju
     * @param str
     * @param match
     * @return
     * @date 2015年12月2日 下午2:19:01
     */
    public static Boolean isNotEmptyAndMatches(String str, String match) {
        try {
            if (isEmpty(str) || isEmpty(match)) {
                return Boolean.FALSE;
            }
            Pattern p = Pattern.compile(match);
            Matcher m = p.matcher(str);
            if (!m.matches()) {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            logger.error("判断一个字段不为空，且符合某个正则表达式失败，str=" + str + ",match=" + match, e);
        }
        return Boolean.TRUE;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static Long calculateTotalvalue(Long price, Long purchnum) {

        Long totalvalue = price * purchnum;

        return totalvalue;

    }

    public static Long calculateTotalvalueAll(Long totalvalue, Long postage) {

        Long calculateTotalvalueAll = totalvalue + postage;

        return calculateTotalvalueAll;

    }

    /**
     * 将ISO-8859-1格式转换为utf8格式
     * 
     * @param arg
     * @return
     */
    public static String toUTF(String arg) {
        if (arg != null && arg.trim().length() > 0) {
            try {
                arg = new String(arg.getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception e) {
            }
        } else {
            arg = "";
        }
        return arg;
    }

    /**
     * 微信将参数放入发送模板
     * 
     * @param name
     * @param value
     * @return
     */
    public static Map<String, String> getDataMap(String name, String value) {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("name", name);
        dataMap.put("value", value);
        return dataMap;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        // 防止 double 输出科学计数法
        if (obj instanceof Double) {
            return obj.toString();
        } else if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            return bigDecimal.toPlainString();
        }
        return obj.toString();
    }

    public static Boolean toBoolean(Object obj) {
        String s = toString(obj);
        if (PubUtil.isEmpty(s)) {
            return null;
        }
        s = s.trim().toLowerCase();
        if ("true".equals(s) || "1".equals(s)) {
            return Boolean.TRUE;
        } else if ("false".equals(s) || "0".equals(s)) {
            return Boolean.FALSE;
        } else {
            return null;
        }
    }

    public static Integer toInteger(Object obj) {
        try {
            return Integer.valueOf(numFormat(obj, 0));
        } catch (Exception e) {
            return null;
        }
    }

    public static Long toLong(Object obj) {
        // 支持double 类型取整
        try {
            return Long.valueOf(numFormat(obj, 0));
        } catch (Exception e) {
            return null;
        }
    }

    public static Short toShort(Object obj) {
        // 支持Short 类型取整
        try {
            return Short.valueOf(numFormat(obj, 0));
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        } else {
            String dateStr = toString(obj);
            if (isEmpty(dateStr)) {
                return null;
            }
            return str2Date(dateStr.trim());
        }
    }

    public static Byte toByte(Object obj) {
        try {
            return Byte.valueOf(numFormat(obj, 0));
        } catch (Exception e) {
            logger.warn("转成成byte数据失败，传入数据未：" + obj, e);
            return null;
        }
    }

    public static Double toDouble(Object obj) {
        String s = toString(obj);
        if (s == null) {
            return null;
        }
        try {
            return Double.valueOf(s.trim());
        } catch (Exception e) {
            logger.warn("转成成Double数据失败，传入数据未：" + s, e);
            return null;
        }
    }

    public static Float toFloat(Object obj) {
        String s = toString(obj);
        if (s == null) {
            return null;
        }
        try {
            return Float.valueOf(s.trim());
        } catch (Exception e) {
            logger.warn("转成成Float数据失败，传入数据未：" + s, e);
            return null;
        }
    }

    /**
     * 直接获取UUID
     * 
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 直接获取UUID
     * 
     * @return
     */
    public static String getUUIDShort() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 获取随机数字
     * 
     * @return
     */
    public static String getRandNum(Integer count) {
        if (count <= 0) {
            return "";
        }
        String rStr = "";
        for (int i = 0; i < count; i++) {
            Random rd = new Random();
            rStr += rd.nextInt(9);
        }
        return rStr;
    }

    /**
     * 获取随机字符+数字
     * 
     * @return
     */
    public static String getRandStr(Integer count) {
        StringBuffer stringBuffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer rStringBuffer = new StringBuffer();
        if (count <= 0) {
            return "";
        }
        Random random = new Random();
        int range = stringBuffer.length();
        for (int i = 0; i < count; i++) {
            rStringBuffer.append(stringBuffer.charAt(random.nextInt(range)));
        }
        return rStringBuffer.toString();
    }

    /**
     * 存储文件
     * 
     * @param data
     * @param file
     * @return
     */
    public static boolean saveFile(byte[] data, String file) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(file));
            outputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("存储文件失败！", e);
            return false;
        } finally {
            try {
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));

        } catch (ParseException e) {
            logger.error("计算日期相差天数失败：smdate" + smdate + ",bdate" + bdate, e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date yearsAfter(Date smdate, int years) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(smdate);
        calendar.add(Calendar.YEAR, years);// 把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime(); // 这个时间就是日期往后推一天的结果
    }

    public static Date monthsAfter(Date smdate, int months) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(smdate);
        calendar.add(Calendar.MONTH, months);// 把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime(); // 这个时间就是日期往后推一天的结果
    }

    public static Date daysAfter(Date smdate, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(smdate);
        calendar.add(Calendar.DATE, days);// 把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime(); // 这个时间就是日期往后推一天的结果
    }

    public static Date secondsAfter(Date smdate, Integer second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(smdate);
        calendar.add(Calendar.SECOND, second);// 把日期往后增加秒.整数往后推,负数往前移动
        return calendar.getTime(); // 这个时间就是日期往后推一天的结果
    }


    /**
     * 提供精确的加法运算。
     * 
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static Double add(Object v1, Object v2) {
        Double d = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        try {
            b1 = new BigDecimal(PubUtil.toString(v1).trim());
            b2 = new BigDecimal(PubUtil.toString(v2).trim());
            d = b1.add(b2).doubleValue();
        } catch (Exception e) {
            logger.warn("计算出错,传入v1:" + v1 + ",v2:" + v2, e);
        }
        return d;
    }

    /**
     * 提供精确的减法运算。
     * 
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static Double sub(Object v1, Object v2) {
        Double d = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        try {
            b1 = new BigDecimal(PubUtil.toString(v1).trim());
            b2 = new BigDecimal(PubUtil.toString(v2).trim());
            d = b1.subtract(b2).doubleValue();
        } catch (Exception e) {
            logger.warn("计算出错,传入v1:" + v1 + ",v2:" + v2, e);
        }
        return d;
    }

    /**
     * 提供精确的乘法运算。
     * 
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Object v1, Object v2) {
        Double d = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        try {
            b1 = new BigDecimal(PubUtil.toString(v1).trim());
            b2 = new BigDecimal(PubUtil.toString(v2).trim());
            d = b1.multiply(b2).doubleValue();
        } catch (Exception e) {
            logger.warn("计算出错,传入v1:" + v1 + ",v2:" + v2, e);
        }
        return d;
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * 
     * @param v1 除数
     * @param v2 被除数
     * @return 两个参数的商
     */
    public static Double div(Object v1, Object v2) {
        return div(v1, v2, 2);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * 
     * @param v1 除数
     * @param v2 被除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double div(Object v1, Object v2, Integer scale) {
        Double d = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        try {
            b1 = new BigDecimal(PubUtil.toString(v1).trim());
            b2 = new BigDecimal(PubUtil.toString(v2).trim());
            d = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            logger.warn("计算出错,传入v1:" + v1 + ",v2:" + v2 + ",scale:" + scale, e);
        }
        return d;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(Object v, Integer scale) {
        return div(v, "1", scale);
    }

    /**
     * numFormat:格式化数字为文本
     *
     * @author zhuangjiaju
     * @param v
     * @param scale
     * @return
     * @date 2015年11月26日 下午2:26:53
     */
    public static String numFormat(Object v, Integer scale) {
        String s = "";
        BigDecimal b = null;
        try {
            String vStr = toString(v);
            if (isEmpty(vStr)) {
                vStr = "0";
            }
            b = new BigDecimal(vStr.trim());
            String ds;
            if (scale > 0) {
                ds = "0." + leftPad("", scale, "0");
            } else if (scale == 0) {
                ds = "0";
            } else {
                return s;
            }
            DecimalFormat df = new DecimalFormat(ds);
            df.setRoundingMode(RoundingMode.HALF_UP);
            s = df.format(b);
        } catch (Exception e) {
            logger.warn("格式化出错,传入v:" + v + ",scale:" + scale, e);
        }
        return s;
    }

    /**
     * numFormat:格式化数字为文本
     *
     * @author zhuangjiaju
     * @param v
     * @return
     * @date 2015年11月26日 下午2:26:53
     */
    public static String numFormat(Object v) {
        return numFormat(v, 2);
    }

    /**
     * 取出sql中的参数
     * 
     * @param str
     * @return
     */
    public static List<String> getParam(String str) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(str)) {
            return list;
        }
        while (str.indexOf("${") > -1) {
            str = str.substring(str.indexOf("${") + 2, str.length());
            list.add(str.substring(0, str.indexOf("}")));
            str = str.substring(str.indexOf("}") + 1, str.length());
        }
        return list;
    }

    /**
     * 首字母大写
     * 
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 生成get方法
     * 
     * @param str
     * @return
     */
    public static String createGet(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return "get" + firstUpperCase(str);
    }

    /**
     * 生成MD5校验
     * 
     * @param source
     * @return
     */
    public static String generalMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串
        } catch (Exception e) {
            logger.error("生成MD5失败！", e);
        }
        return s;
    }

    /**
     * 生成MD5校验
     * 
     * @return
     */
    public static String generalMD5(String comKey, String str) {
        try {
            return generalMD5((comKey + str).getBytes("GBK"));
        } catch (Exception e) {
            logger.error("生成MD5校验码失败");
        }
        return null;
    }

    /**
     * 方法介绍: 将一个实体对象转换为xml</br>
     * 注意事项：无</br>
     * 创建日期: 2016年4月12日 下午8:17:47</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param obj
     * @return 如果异常，则返回null
     */
    public static String obj2Xml(Object obj, String charSet) {
        String rStr = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, charSet);
            marshaller.marshal(obj, byteArrayOutputStream);
            rStr = byteArrayOutputStream.toString(charSet);
        } catch (Exception e) {
            logger.error("将对象转换为xml失败", e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (Exception e1) {
            }
        }
        return rStr;
    }

    /**
     * 方法介绍: 将xml转换为一个实体对象</br>
     * 注意事项：无</br>
     * 创建日期: 2016年4月12日 下午8:18:30</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param xml
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Obj(String xml, Class<T> clazz, String charSet) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(xml.getBytes(charSet));
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(byteArrayInputStream);
        } catch (Exception e) {
            logger.warn("将对象转换为xml失败", e);
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (Exception e1) {
            }
        }
        return null;
    }

    /**
     * 方法介绍: 按照编码获取字节</br>
     * 注意事项：无</br>
     * 创建日期: 2016年5月30日 下午2:45:12</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param content
     * @param charset
     * @return
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (isEmpty(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            logger.warn("按照编码获取字节传入的字符集有误！", e);
            return null;
        }
    }

    /**
     * 方法介绍: 计算年龄</br>
     * 注意事项：无</br>
     * 创建日期: 2016年9月27日 上午11:29:26</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        return getAgeByBirthday(birthday, getCurrDate());
    }

    /**
     * 方法介绍: 计算年龄，endDate代表计算到的年龄</br>
     * 注意事项：无</br>
     * 创建日期: 2016年9月27日 上午11:29:04</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param birthday
     * @param endDate
     * @return
     */
    public static int getAgeByBirthday(Date birthday, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("出生日期在计算日期之后！");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * 方法介绍: 将boolean转成0 或者1</br>
     * 注意事项：无</br>
     * 创建日期: 2016年6月29日 上午10:31:10</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param b
     * @return
     */
    public static String boolean2String(Boolean b) {
        String rStr = null;
        if (b != null) {
            if (b) {
                rStr = "1";
            } else {
                rStr = "0";
            }
        }
        return rStr;
    }


    /**
     * 方法介绍: 换行转换为空格</br>
     * 注意事项：无</br>
     * 创建日期: 2017年3月27日 下午8:13:38</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @param str
     * @return
     */
    public static String repalcWarp2Blank(String str) {
        return StringUtils.replaceAll(str, "\t|\r|\n", " ");
    }

    public static String generateStockKey(String stockCode, Long accountId) {
        return stockCode + "-" + accountId;
    }

    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
        System.out.println(toLong(1.8));
    }
}
