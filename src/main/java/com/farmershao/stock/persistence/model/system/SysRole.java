package com.farmershao.stock.persistence.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SysRole
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
@ApiModel(value="com.farmershao.stock.persistence.model.system.SysRole")
@Getter
@Setter
@ToString
public class SysRole {
    /**
    * 角色id
    */
    @ApiModelProperty(value="角色id")
    private Long id;

    /**
    * 角色名
    */
    @ApiModelProperty(value="角色名")
    private String roleName;

    /**
    * 角色描述
    */
    @ApiModelProperty(value="角色描述")
    private String remark;

    /**
    * 权限列表(英文逗号分隔）
    */
    @ApiModelProperty(value="权限列表(英文逗号分隔）")
    private String permissions;
}