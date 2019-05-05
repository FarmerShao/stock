package com.farmershao.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * StockApplication
 *
 * @author Shao Yu
 * @date 2019/5/5 19:09
 * @since
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.farmershao.stock.persistence.mapper"})
@EnableSwagger2
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
public class StockApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

    }
}
