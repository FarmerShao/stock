package com.farmershao.stock.trade.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件加載類
 *
 * Created by kongkp on 16-8-26.
 */
public class Conf {
    protected static Properties prop = null;

    static {
        prop = loadProperties("classpath:tdx.properties");
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    private static Properties loadProperties(String proPath) {
        InputStream in = null;
        Properties props = new Properties();
        try {
            in = FileUtils.load(proPath);
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } finally {
            CloseableUtils.close(in);
        }
        return props;
    }

    public static Properties properties() {
        return Conf.prop;
    }

}
