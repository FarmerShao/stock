package com.farmershao.stock.web.controller.back;

import com.farmershao.stock.constant.Constant;
import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.PageResponseDto;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.model.system.SysRole;
import com.farmershao.stock.service.system.SysRoleService;
import com.farmershao.stock.web.BackAuthentication;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 后台角色管理
 *
 * @author : Zhao Da
 * @since : 2019/4/29 09:45
 */
@RestController
@RequestMapping(value = "/back/role")
@CrossOrigin(origins = "*", allowedHeaders = "Authorization,loginResult,Access-Control-Allow-Origin, Content-Type,accept")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation(value = "（后台）系统用户角色管理查询", notes = "角色管理查询。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码（1,2,3...）", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页展示数", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "角色ID", required = false, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名", required = false, dataType = "string"),
    })
    @GetMapping("search")
    @BackAuthentication(code = "15")
    public PageResponseDto<SysRole> search(
            HttpServletRequest request,
            @RequestParam("pageNum")int pageNum,
            @RequestParam("pageSize")int pageSize,
            SysRoleDto sysRoleDto
    ) {
        String role = (String) request.getAttribute("role");
        if (!Constant.SYSTEM_ROLE_ID.equals(role)) {
            PageResponseDto<SysRole> page = new PageResponseDto<>();
            page.setData(new ArrayList<>());
            page.setMsg("您不是管理员，没有查看角色信息权限");
            return page;
        }
        PageInfo<SysRole> pageInfo = sysRoleService.selectList(pageNum, pageSize, sysRoleDto);
        return new PageResponseDto<>(pageInfo);
    }

    @ApiOperation(value = "（后台）角色管理更新", notes = "角色管理更新。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "角色ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "角色描述", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "permissions", value = "权限列表(英文逗号分隔）", required = false, dataType = "string"),
    })
    @GetMapping("update")
    @BackAuthentication(code = "16")
    public ResponseDto<Integer> update(
            HttpServletRequest request,
            SysRole sysRole
    ) {
        String role = (String) request.getAttribute("role");
        if (!Constant.SYSTEM_ROLE_ID.equals(role)) {
            ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.FAILURE);
            result.setData(0);
            result.setMsg("您不是管理员，没有查看权限");
            return result;
        }
        ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        Integer data = sysRoleService.update(sysRole);
        result.setData(data);
        return result;
    }
}
