package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.User;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface UserMapper {
    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);
}