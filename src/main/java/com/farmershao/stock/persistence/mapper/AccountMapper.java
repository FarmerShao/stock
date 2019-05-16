package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.Account;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface AccountMapper {
    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);
}