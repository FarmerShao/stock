package com.farmershao.stock.web.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.farmershao.stock.config.CommonConfig;
import com.farmershao.stock.constant.Constant;
import com.farmershao.stock.constant.ResponseCodeEnum;
import com.farmershao.stock.dto.ResponseDto;
import com.farmershao.stock.service.system.SysRoleService;
import com.farmershao.stock.util.JsonUtil;
import com.farmershao.stock.util.JwtUtil;
import com.farmershao.stock.web.BackAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * JWT 鉴权
 *
 * @author : Zhao Da
 * @since : 2019/4/28 10:16
 */
@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private final static String BEARER = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private CommonConfig commonConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteAddr = request.getRemoteAddr();
        String originIpStr = commonConfig.getJwtOriginIp();
        String originIp = "";
        if (!StringUtils.isEmpty(originIpStr)) {
            String [] originIps = originIpStr.split(",");
            for (String origin : originIps) {
                if (origin.contains(remoteAddr)) {
                    originIp = origin;
                    break;
                }
            }
        }
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())){
            log.info("浏览器的预请求的处理..");
            response.setHeader("Access-Control-Allow-Origin", originIp);
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Expose-Headers", "Authorization,loginResult");
            return true;
        } else {
            response.setHeader("Access-Control-Allow-Origin", originIp);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String role = "";
            String loginName = "";
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith(BEARER)) {
                try {
                    Map<String, Claim> claims = jwtUtil.parseToken(authorization.substring(7));
                    if (claims != null) {
                        role = claims.get("role").asString();
                        loginName = claims.get("very").asString();
                    }
                } catch (Exception e) {
                    log.error("令牌有误：com.auth0.jwt.exceptions.JWTVerificationException: parse token error");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return unauthorizedResponse(response, HttpStatus.UNAUTHORIZED.value());
                }
            }
            if (StringUtils.isEmpty(role)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return unauthorizedResponse(response, HttpStatus.UNAUTHORIZED.value());
            }
            if (Constant.SYSTEM_ROLE_ID.equals(role)) {
                request.setAttribute("role", role);
                request.setAttribute("loginName", loginName);
                return true;
            }
            if (handler instanceof HandlerMethod) {
                String permission = "";
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                BackAuthentication authentication = method.getAnnotation(BackAuthentication.class);
                if (authentication != null) {
                    permission = authentication.code();
                }
                if (!sysRoleService.containsPermission(role, permission)) {
                    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                    return unauthorizedResponse(response, HttpStatus.METHOD_NOT_ALLOWED.value());
                }
            }
            request.setAttribute("role", role);
            request.setAttribute("loginName", loginName);
            return true;
        }
    }

    private boolean unauthorizedResponse(HttpServletResponse response, int statusCode) {
        try {
            PrintWriter out = response.getWriter();
            ResponseDto responseDto = new ResponseDto<>(ResponseCodeEnum.FAILURE);
            responseDto.setCode(statusCode);
            responseDto.setMsg("无权访问!");
            out.write(JsonUtil.toJson(responseDto));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("JWT 权限处理异常：", e);
        }
        return false;
    }

}
