package com.farmershao.stock.dto;



import com.farmershao.stock.persistence.model.RiskManagement;
import com.farmershao.stock.persistence.model.StrategyLeverage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * RiskManagementDto
 *
 * @author Shao Yu
 * @since 2019/5/19 19:12
 **/
@Setter
@Getter
public class RiskManagementDto {

    /** 风控管理 */
    private RiskManagement management;
    /** 策略匹配杠杆倍数 */
    private List<StrategyLeverage> strategyLeverages;

}
