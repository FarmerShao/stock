package com.farmershao.stock.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wzm
 * @date 2017年07月12日.
 */
public class SpringManager {

    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static <T> T  getBean(Class<T> cls){
         return applicationContext.getBean(cls);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static  <T> T getBean(String name, Class<T> requiredType){
        return applicationContext.getBean(name, requiredType);
    }
}
