package com.farmershao.stock.trade.constant;

/**
 * NameValue
 *
 * @author Shao Yu
 * @since 2018/4/25 9:30
 **/
public interface NameValue<N,V> {

    N getName();

    V getValue();

    Object getObject();
}
