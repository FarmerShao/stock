package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.User;

/**
 * UserMapper
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
public interface UserMapper {

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

}