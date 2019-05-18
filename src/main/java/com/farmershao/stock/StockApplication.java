package com.farmershao.stock;

import com.farmershao.stock.util.SpringManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * StockApplication
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.farmershao.stock.persistence.mapper"})
@EnableSwagger2
@EnableScheduling
public class StockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StockApplication.class, args);
        SpringManager.setApplicationContext(applicationContext);
    }
}
