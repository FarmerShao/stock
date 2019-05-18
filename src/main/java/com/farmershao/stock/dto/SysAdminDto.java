package com.farmershao.stock.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * SysAdminDto
 *
 * @author zhaoda
 * @Description 系统用户查询dto
 * @since 2019/5/18 14:09
 **/
@ApiModel
@Getter
@Setter
public class SysAdminDto {

    @ApiModelProperty(value="用户ID",name="id")
    private Long id;

    @ApiModelProperty(value="用户名",name="userName")
    private String userName;

    @ApiModelProperty(value="角色id",name="roleId")
    private Long roleId;

    @ApiModelProperty(value="用户密码（md5加密）",name="userPwd")
    private String userPwd;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;
}
