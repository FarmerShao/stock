package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.Manager;
import com.farmershao.stock.persistence.mapper.ManagerMapper;
/**
 * Created by ShaoYu on 2019/5/16.
 */

@Service
public class ManagerService{

    @Resource
    private ManagerMapper managerMapper;

    
    public int insertSelective(Manager record) {
        return managerMapper.insertSelective(record);
    }

    
    public Manager selectByPrimaryKey(Integer id) {
        return managerMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Manager record) {
        return managerMapper.updateByPrimaryKeySelective(record);
    }

}
