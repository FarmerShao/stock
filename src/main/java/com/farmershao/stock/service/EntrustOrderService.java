package com.farmershao.stock.service;

import com.farmershao.stock.constant.ResponseCodeEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.EntrustOrder;
import com.farmershao.stock.persistence.mapper.EntrustOrderMapper;

import java.math.BigDecimal;

/**
 * EntrustOrderService： 委托单服务
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
@Service
public class EntrustOrderService{

    @Resource
    private EntrustOrderMapper entrustOrderMapper;


    public int buy(Long userId, String stockId, BigDecimal price, Integer lots, Integer marginRate) {
        // 查询用户资金

        //
        return 0;
    }



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
