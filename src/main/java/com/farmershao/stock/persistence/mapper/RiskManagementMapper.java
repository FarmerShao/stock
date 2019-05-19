package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.RiskManagement;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface RiskManagementMapper {
    int insertSelective(RiskManagement record);

    RiskManagement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RiskManagement record);

    /**
     * 查询风控数据库配置
     * @return  RiskManagement
     */
    RiskManagement find();
}