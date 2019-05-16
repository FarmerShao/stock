package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.Manager;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface ManagerMapper {
    int insertSelective(Manager record);

    Manager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manager record);
}