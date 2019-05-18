package com.farmershao.stock.web.controller.back;

import com.farmershao.stock.constant.Constant;
import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.persistence.model.system.SysPermission;
import com.farmershao.stock.service.system.SysPermissionService;
import com.farmershao.stock.web.BackAuthentication;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Zhao Da
 * @since : 2019/4/29 11:30
 */
@RestController
@RequestMapping(value = "/back/permission")
@CrossOrigin(origins = "*", allowedHeaders = "Authorization,loginResult,Access-Control-Allow-Origin, Content-Type,accept")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "（后台）系统功能权限查询", notes = "系统功能权限查询。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer token", required = true, dataType = "string"),
    })
    @GetMapping("search")
    @BackAuthentication(code = "17")
    public ResponseDto<Map<String, List<SysPermission>>> search(HttpServletRequest request, HttpServletResponse response) {
        String role = (String) request.getAttribute("role");
        if (!Constant.SYSTEM_ROLE_ID.equals(role)) {
            ResponseDto<Map<String, List<SysPermission>>> result = new ResponseDto<>(ResponseCodeEnum.FAILURE);
            result.setData(new HashMap<>(2));
            result.setMsg("您不是管理员，没有查看权限");
            return result;
        }
        Map<String, List<SysPermission>> map =  sysPermissionService.findPermissionMap();
        ResponseDto<Map<String, List<SysPermission>>> result = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        result.setData(map);
        return result;
    }

    @ApiOperation(value = "（后台）系统功能权限查询", notes = "系统功能权限查询。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token", required = true, dataType = "string"),
    })
    @GetMapping("clear")
    @BackAuthentication(code = "17")
    public ResponseDto<Integer> clear(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!Constant.SYSTEM_ROLE_ID.equals(role)) {
            ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.FAILURE);
            result.setData(0);
            result.setMsg("您不是管理员，没有查看权限");
            return result;
        }
        ResponseDto<Integer> result = new ResponseDto<>(ResponseCodeEnum.SUCCESS);
        Integer data = sysPermissionService.clear();
        result.setData(data);
        return result;
    }
}
