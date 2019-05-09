package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.Account;

/**
 * AccountMapper
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
public interface AccountMapper {

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

}