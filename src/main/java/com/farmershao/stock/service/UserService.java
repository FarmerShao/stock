package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.User;
import com.farmershao.stock.persistence.mapper.UserMapper;
/**
 * Created by ShaoYu on 2019/5/16.
 */

@Service
public class UserService{

    @Resource
    private UserMapper userMapper;

    
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

}
