package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ShaoYu on 2019/5/16.
 */

@ApiModel(value="com.farmershao.stock.persistence.model.Manager")
@Getter
@Setter
@ToString
public class Manager {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 姓名
    */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
    * 手机号
    */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
    * 状态：1 启用 2 禁用
    */
    @ApiModelProperty(value="状态：1 启用 2 禁用")
    private Byte status;

    /**
    * 客户数 
    */
    @ApiModelProperty(value="客户数 ")
    private Integer count;
}