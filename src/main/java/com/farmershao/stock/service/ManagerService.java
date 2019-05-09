package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.Manager;
import com.farmershao.stock.persistence.mapper.ManagerMapper;

/**
 * ManagerService
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Service
public class ManagerService{

    @Resource
    private ManagerMapper managerMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return managerMapper.deleteByPrimaryKey(id);
    }

    
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
