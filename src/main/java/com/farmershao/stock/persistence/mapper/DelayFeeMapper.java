package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.DelayFee;


/**
 * DelayFeeMapper
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
public interface DelayFeeMapper {

    int insertSelective(DelayFee record);

    DelayFee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DelayFee record);
}