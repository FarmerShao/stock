package com.farmershao.stock.web.controller.back;

import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.PageResponseDto;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.dto.SysAdminDto;
import com.farmershao.stock.persistence.model.system.SysAdmin;
import com.farmershao.stock.service.system.SysAdminService;
import com.farmershao.stock.util.JwtUtil;
import com.farmershao.stock.web.BackAuthentication;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台用户管理
 *
 * @author : Zhao Da
 * @since : 2019/4/29 14:42
 */
@RestController
@RequestMapping(value = "/back/sysAdmin")
public class SysAdminController {

    @Resource
    private SysAdminService sysAdminService;
    @Resource
    private JwtUtil jwtUtil;

    @ApiOperation(value = "（后台）用户管理-查询", notes = "用户管理。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码（1,2,3...）", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页展示数", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "角色id", required = false, dataType = "Long"),})
    @GetMapping("search")
    @BackAuthentication(code = "18")
    public PageResponseDto<SysAdmin> search(@RequestParam("pageNum")int pageNum,
                                            @RequestParam("pageSize")int pageSize,
                                            SysAdminDto sysAdminDto)
    {
        PageInfo<SysAdmin> pageInfo = sysAdminService.selectList(pageNum, pageSize, sysAdminDto);
        return new PageResponseDto<>(pageInfo);
    }

    @ApiOperation(value = "（后台）用户管理-添加", notes = "添加用户,返回插入数量>0成功。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "用户角色ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "string"),
    })
    @RequestMapping(value = "add" ,method = RequestMethod.POST)
    @BackAuthentication(code = "21")
    public ResponseDto<Integer> add(SysAdminDto sysAdminDto) {
        ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        Integer data = sysAdminService.insert(sysAdminDto);
        result.setData(data);
        return result;
    }

    @ApiOperation(value = "（后台）用户管理-修改", notes = "更新用户信息。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "用户角色ID", required = false, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = false, dataType = "string"),
    })
    @RequestMapping(value = "update" ,method = RequestMethod.POST)
    @BackAuthentication(code = "22")
    public ResponseDto<Integer> update(SysAdminDto sysAdminDto) {
        ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        Integer data = sysAdminService.update(sysAdminDto);
        result.setData(data);
        return result;
    }

    @ApiOperation(value = "（后台）用户登录", notes = "后台登录。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "adminName", value = "用户名", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "authCode", value = "动态校验码", dataType = "string")
    })
    @RequestMapping(value = "login", method = RequestMethod.HEAD)
    public ResponseDto<String> login(
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "adminName") String adminName,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "authCode", required = false) Long authCode) {
        SysAdmin sysAdmin = sysAdminService.login(request, adminName, password, authCode);
        if (sysAdmin != null && sysAdmin.getRoleId() == null) {
            response.addHeader("Authorization", "Bearer ");
            response.addHeader("loginResult", sysAdmin.getRemark());
            return new ResponseDto<>(ResponseCodeEnum.FAILURE.getCode(), sysAdmin.getRemark());
        }
        ResponseDto<String> responseDto = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        response.addHeader("loginResult", "0");
        response.addHeader("Authorization",
                "Bearer " + jwtUtil.createTokenWithClaim(sysAdmin.getUserName(),String.valueOf(sysAdmin.getRoleId()), sysAdmin.getRoleName()));
        responseDto.setData("");
        return responseDto;
    }
}
