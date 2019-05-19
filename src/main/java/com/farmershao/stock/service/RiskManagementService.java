package com.farmershao.stock.service;

import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.RiskManagementDto;
import com.farmershao.stock.persistence.mapper.RiskManagementMapper;
import com.farmershao.stock.persistence.mapper.StrategyLeverageMapper;
import com.farmershao.stock.persistence.model.RiskManagement;
import com.farmershao.stock.persistence.model.StrategyLeverage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.farmershao.stock.constant.ResponseCodeEnum.*;

/**
 * 风控管理服务
 *
 * @author Shao Yu
 * @since 2019/5/19 18:28
 **/
@Service
@Slf4j
public class RiskManagementService {

    private final static Integer MAX_LEVERAGE_SIZE = 3;

    @Resource
    private RiskManagementMapper riskManagementMapper;
    @Resource
    private StrategyLeverageMapper strategyLeverageMapper;

    /**
     * 查询风控配置信息
     *
     * @return RiskManagementDto
     */
    public RiskManagementDto find() {
        RiskManagementDto managementDto = new RiskManagementDto();
        try {
            RiskManagement management = riskManagementMapper.find();
            if (management != null) {
                List<StrategyLeverage> leverages = strategyLeverageMapper.findAll();
                managementDto.setManagement(management);
                managementDto.setStrategyLeverages(leverages);
            }
        } catch (Exception e) {
            log.error("风控管理", e);
        }
        return managementDto;
    }

    /**
     * 保存风控配置信息
     *
     * @param management 风控管理
     * @return ResponseCodeEnum
     */
    public ResponseCodeEnum saveManagement(RiskManagement management) {
        try {
            RiskManagement dbManagement = riskManagementMapper.find();
            if (dbManagement == null) {
                //添加风控管理
                riskManagementMapper.insertSelective(management);
            } else {
                management.setId(dbManagement.getId());
                riskManagementMapper.updateByPrimaryKeySelective(management);
            }
        } catch (Exception e) {
            log.error("保存风控配置异常：", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    /**
     * 创建策略匹配杠杆倍数
     *
     * @param leverage 杠杆倍数
     * @return ResponseCodeEnum
     */
    public ResponseCodeEnum createStrategyLeverage(StrategyLeverage leverage) {
        try {
            List<StrategyLeverage> leverages = strategyLeverageMapper.findAll();
            if (leverages.size() >= MAX_LEVERAGE_SIZE) {
                return MAX_MARGIN_RATE_NUMBER;
            } else {
                strategyLeverageMapper.insert(leverage);
            }
        } catch (Exception e) {
            log.error("保存策略匹配杠杆倍数异常：", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    /**
     * 修改策略匹配杠杆倍数
     *
     * @param leverage 杠杆倍数
     * @return ResponseCodeEnum
     */
    public ResponseCodeEnum updateStrategyLeverage(StrategyLeverage leverage) {
        try {
            strategyLeverageMapper.update(leverage);
        } catch (Exception e) {
            log.error("修改策略匹配杠杆倍数异常：", e);
            return FAILURE;
        }
        return SUCCESS;
    }

    /**
     * 删除策略匹配杠杆倍数
     *
     * @param id 杠杆倍数ID
     * @return ResponseCodeEnum
     */
    public ResponseCodeEnum deleteStrategyLeverage(Long id) {
        try {
            strategyLeverageMapper.delete(id);
        } catch (Exception e) {
            log.error("删除策略匹配杠杆倍数异常：", e);
            return FAILURE;
        }
        return SUCCESS;
    }


}
