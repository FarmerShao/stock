package com.farmershao.stock.web.controller;

import com.farmershao.stock.config.CommonConfig;
import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author Shao Yu
 * @date 2019/5/5 19:14
 * @since
 **/
@RestController
@Slf4j
public class Controller {

    @Autowired
    private CommonConfig config;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseDto<Object> hello(){
        String jwtSecret = config.getJwtSecret();
        log.info("jwtSecret:[{}]", jwtSecret);
        return new ResponseDto<>(ResponseCodeEnum.SUCCESS);
    }

}
