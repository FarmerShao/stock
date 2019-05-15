package com.farmershao.stock.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.farmershao.stock.persistence.model.DelayFee;
import com.farmershao.stock.persistence.mapper.DelayFeeMapper;

/**
 * DelayFeeService : 递延费服务
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
@Service
public class DelayFeeService{

    @Resource
    private DelayFeeMapper delayFeeMapper;

    
    public int insertSelective(DelayFee record) {
        return delayFeeMapper.insertSelective(record);
    }

    
    public DelayFee selectByPrimaryKey(Integer id) {
        return delayFeeMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(DelayFee record) {
        return delayFeeMapper.updateByPrimaryKeySelective(record);
    }

}
