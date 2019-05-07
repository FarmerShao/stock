package com.farmershao.stock.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * SwaggerConfig
 *
 * @author Shao Yu
 * @since 2019/5/7 13:26
 **/
@Configuration
@Setter
@Getter
public class SwaggerConfig {

    @Value("${system.dev.mode}")
    private boolean systemDevMode;

    @Bean
    public Docket createRestApi() {
        if (systemDevMode) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.farmershao.stock.web.controller"))
                    .paths(PathSelectors.any())
                    .build();
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.farmershao.stock.web.controller"))
                    .paths(PathSelectors.none())
                    .build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("股票系统API")
                .description("stock RESTFul Api")
                .version("1.0.0")
                .build();
    }
}

