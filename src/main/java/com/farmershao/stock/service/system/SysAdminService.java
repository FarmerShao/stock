package com.farmershao.stock.service.system;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.mapper.system.SysAdminMapper;
import com.farmershao.stock.persistence.model.system.SysAdmin;

/**
 * SysAdminService
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Service
public class SysAdminService{

    @Resource
    private SysAdminMapper sysAdminMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return sysAdminMapper.deleteByPrimaryKey(id);
    }

    
    public int insertSelective(SysAdmin record) {
        return sysAdminMapper.insertSelective(record);
    }

    
    public SysAdmin selectByPrimaryKey(Integer id) {
        return sysAdminMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(SysAdmin record) {
        return sysAdminMapper.updateByPrimaryKeySelective(record);
    }

}
