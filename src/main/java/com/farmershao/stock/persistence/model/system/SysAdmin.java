package com.farmershao.stock.persistence.model.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SysAdmin
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
@ApiModel(value="com.farmershao.stock.persistence.model.system.SysAdmin")
@Getter
@Setter
@ToString
public class SysAdmin {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 用户创建时间
    */
    @ApiModelProperty(value="用户创建时间")
    private Date createdAt;

    /**
    * 用户修改时间
    */
    @ApiModelProperty(value="用户修改时间")
    private Date updatedAt;

    /**
    * 角色ID
    */
    @ApiModelProperty(value="角色ID")
    private Integer roleId;

    /**
    * 用户名称
    */
    @ApiModelProperty(value="用户名称")
    private String userName;

    /**
    * 用户密码
    */
    @ApiModelProperty(value="用户密码")
    private String userPwd;

    /**
    * 最后登录IP
    */
    @ApiModelProperty(value="最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value="null")
    private Date lastLoginTime;

    /**
    * 角色类型 ：1.系统管理员
    */
    @ApiModelProperty(value="角色类型 ：1.系统管理员")
    private Byte roleType;

    /**
    * 1.删除 2.未删除
    */
    @ApiModelProperty(value="1.删除 2.未删除")
    private Byte disableFlag;

    @ApiModelProperty(value="null")
    private String loginKey;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;
}