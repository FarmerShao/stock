package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.HoldingOrder;
import com.farmershao.stock.persistence.mapper.HoldingOrderMapper;

/**
 * HoldingOrderService： 持仓单服务
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
@Service
public class HoldingOrderService{

    @Resource
    private HoldingOrderMapper holdingOrderMapper;

    
    public int insertSelective(HoldingOrder record) {
        return holdingOrderMapper.insertSelective(record);
    }

    
    public HoldingOrder selectByPrimaryKey(Integer id) {
        return holdingOrderMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(HoldingOrder record) {
        return holdingOrderMapper.updateByPrimaryKeySelective(record);
    }

}
