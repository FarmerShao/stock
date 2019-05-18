package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.mapper.AccountMapper;
import com.farmershao.stock.persistence.model.Account;
/**
 * Created by ShaoYu on 2019/5/16.
 */

@Service
public class AccountService{

    @Resource
    private AccountMapper accountMapper;

    
    public int insertSelective(Account record) {
        return accountMapper.insertSelective(record);
    }

    
    public Account selectByPrimaryKey(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Account record) {
        return accountMapper.updateByPrimaryKeySelective(record);
    }

}
