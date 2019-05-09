package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.Manager;

/**
 * ManagerMapper
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
public interface ManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Manager record);

    Manager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manager record);
}