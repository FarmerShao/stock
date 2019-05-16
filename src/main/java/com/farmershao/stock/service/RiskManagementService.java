package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.RiskManagement;
import com.farmershao.stock.persistence.mapper.RiskManagementMapper;
/**
 * Created by ShaoYu on 2019/5/16.
 */

@Service
public class RiskManagementService{

    @Resource
    private RiskManagementMapper riskManagementMapper;

    
    public int insertSelective(RiskManagement record) {
        return riskManagementMapper.insertSelective(record);
    }

    
    public RiskManagement selectByPrimaryKey(Integer id) {
        return riskManagementMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(RiskManagement record) {
        return riskManagementMapper.updateByPrimaryKeySelective(record);
    }

}
