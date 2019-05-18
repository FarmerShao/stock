package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.EntrustOrder;
import com.farmershao.stock.persistence.mapper.EntrustOrderMapper;
/**
 * Created by ShaoYu on 2019/5/16.
 */

@Service
public class EntrustOrderService{

    @Resource
    private EntrustOrderMapper entrustOrderMapper;

    
    public int insertSelective(EntrustOrder record) {
        return entrustOrderMapper.insertSelective(record);
    }

    
    public EntrustOrder selectByPrimaryKey(Integer id) {
        return entrustOrderMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(EntrustOrder record) {
        return entrustOrderMapper.updateByPrimaryKeySelective(record);
    }

}
