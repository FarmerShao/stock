package com.farmershao.stock.web.controller.back;

import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.dto.RiskManagementDto;
import com.farmershao.stock.persistence.model.RiskManagement;
import com.farmershao.stock.persistence.model.StrategyLeverage;
import com.farmershao.stock.service.RiskManagementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 风控设置Controller
 *
 * @author Shao Yu
 * @since 2018/5/5 14:57
 **/
@RestController
@RequestMapping(value = "/back/risk", method = RequestMethod.GET)
public class RiskManagementController {

    @Resource
    private RiskManagementService managementService;

    @RequestMapping(value = "/management/", method = RequestMethod.GET)
    public ResponseDto<RiskManagementDto> index() {
        RiskManagementDto managementDto = managementService.find();
        ResponseDto<RiskManagementDto> responseDto = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        responseDto.setData(managementDto);
        return responseDto;
    }

    @ResponseBody
    @RequestMapping(value = "/management/", method = RequestMethod.POST)
    public ResponseDto save(@RequestParam BigDecimal minMargin, @RequestParam BigDecimal strategyMaxPoint,
                            @RequestParam BigDecimal userMaxPoint, @RequestParam BigDecimal userMaxBuy,
                            @RequestParam BigDecimal userMaxLose, @RequestParam BigDecimal userMaxIncrease) {
        RiskManagement management = new RiskManagement();
        management.setMinMargin(minMargin);
        management.setStrategyMaxPoint(strategyMaxPoint);
        management.setUserMaxPoint(userMaxPoint);
        management.setUserMaxBuy(userMaxBuy);
        management.setUserMaxIncrease(userMaxIncrease);
        management.setUserMaxLose(userMaxLose);
        ResponseCodeEnum responseCodeEnum = managementService.saveManagement(management);
        return new ResponseDto<>(responseCodeEnum);
    }


    @ResponseBody
    @RequestMapping(value = "/leverage/", method = RequestMethod.DELETE)
    public ResponseDto deleteLeverage(@RequestParam Long id) {
        ResponseCodeEnum responseCodeEnum = managementService.deleteStrategyLeverage(id);
        return new ResponseDto<>(responseCodeEnum);
    }

    @ResponseBody
    @RequestMapping(value = "/leverage/", method = RequestMethod.POST)
    public ResponseDto addLeverage(@RequestParam Integer defaultTag,
                                   @RequestParam BigDecimal defaultLeverage,
                                   @RequestParam BigDecimal maxStopLossRate,
                                   @RequestParam BigDecimal warningRate) {
        StrategyLeverage leverage = new StrategyLeverage();
        leverage.setDefaultTag(defaultTag);
        leverage.setMaxStopLossRate(maxStopLossRate);
        leverage.setWarningRate(warningRate);
        leverage.setDefaultLeverage(defaultLeverage);
        ResponseCodeEnum responseCodeEnum = managementService.createStrategyLeverage(leverage);
        return new ResponseDto<>(responseCodeEnum);
    }

}
