package com.farmershao.stock.service.system;

import com.farmershao.stock.constant.Constant;
import com.farmershao.stock.constant.SysAdminEnums;
import com.farmershao.stock.dto.SysAdminDto;
import com.farmershao.stock.persistence.mapper.system.SysAdminMapper;
import com.farmershao.stock.persistence.model.system.SysAdmin;
import com.farmershao.stock.persistence.model.system.SysRole;
import com.farmershao.stock.util.IpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * SysAdminService
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Service
@Slf4j
public class SysAdminService{

    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysConfigService sysConfigService;

    public int deleteByPrimaryKey(Long id) {
        return sysAdminMapper.deleteByPrimaryKey(id);
    }
    
    public int insertSelective(SysAdmin record) {
        return sysAdminMapper.insertSelective(record);
    }
    
    public SysAdmin selectByPrimaryKey(Long id) {
        return sysAdminMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysAdmin record) {
        return sysAdminMapper.updateByPrimaryKeySelective(record);
    }

    public PageInfo<SysAdmin> selectList(int pageNum, int pageSize, SysAdminDto sysAdminDto) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> sysAdminMapper.selectList(sysAdminDto));
    }

    /**
     * 根据用户名获取系统用户
     * @param name 用户名
     * @return 系统用户
     */
    private SysAdmin findByName(String name) {
        if (StringUtils.isBlank(name)) {return null;}
        SysAdmin sysAdmin = sysAdminMapper.findByName(name);
        if (sysAdmin != null) {
            SysRole sysRole = sysRoleService.findById(sysAdmin.getRoleId());
            if (sysRole != null) {
                sysAdmin.setRoleName(StringUtils.isNotBlank(sysRole.getRoleName()) ? sysRole.getRoleName() : "");
            }
        }
        return sysAdmin;
    }

    /**
     * 根据用户名和密码获取系统用户
     * @param name 用户名
     * @param password 密码
     * @return 系统用户
     */
    private SysAdmin findByNameAndPwd(String name, String password) {
        if (StringUtils.isBlank(name)) {return null;}
        SysAdmin sysAdmin = sysAdminMapper.findByNameAndPwd(name, DigestUtils.md5Hex(password));
        if (sysAdmin != null) {
            SysRole sysRole = sysRoleService.findById(sysAdmin.getRoleId());
            if (sysRole != null) {
                sysAdmin.setRoleName(StringUtils.isNotBlank(sysRole.getRoleName()) ? sysRole.getRoleName() : "");
            } else {
                sysAdmin.setRoleName(Constant.DEFAULT_ROLE_NAME);
            }
        }
        return sysAdmin;
    }

    public String changePassword(String oldPassword, String newPassword, Long id) {
        SysAdmin sysAdmin = new SysAdmin();
        try {
            sysAdmin = sysAdminMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("sysAdminService.changePassword:", e);
            return "系统错误";
        }
        if (sysAdmin == null || sysAdmin.getUserPwd() == null || !sysAdmin.getUserPwd().equals(oldPassword)) {
            return "原密码输入错误";
        }
        sysAdmin.setUserPwd(DigestUtils.md5Hex(newPassword));
        try {
            sysAdminMapper.updatePwdByPrimaryKey(sysAdmin);
        } catch (Exception e) {
            log.error("sysAdminService.changePassword:", e);
            return "系统错误";
        }
        return "";
    }

    public String resetPassword(Long id) {
        SysAdmin sysAdmin = new SysAdmin();
        try {
            sysAdmin = sysAdminMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            log.error("sysAdminService.resetPassword:", e);
            return "系统错误";
        }
        sysAdmin.setUserPwd(DigestUtils.md5Hex(Constant.BACK_USER_DEFAULT_PASSWORD));
        try {
            sysAdminMapper.updatePwdByPrimaryKey(sysAdmin);
        } catch (Exception e) {
            log.error("sysAdminService.resetPassword:", e);
            return "系统错误";
        }
        return "";
    }

    /**
     * 新建用户
     *
     * @return 插入数量
     */
    public int insert(SysAdminDto sysAdminDto) {
        if (sysAdminDto != null) {
            try {
                SysAdmin sysAdmin = convertToSysAdmin(sysAdminDto);
                sysAdmin.setUserPwd(DigestUtils.md5Hex(Constant.BACK_USER_DEFAULT_PASSWORD));
                return sysAdminMapper.insertSelective(sysAdmin);
            } catch (SqlSessionException e) {
                log.error("用户添加异常 sysAdminService.insert()", e);
            }
        }
        return 0;
    }

    /**
     * 活动关联关系更新
     *
     * @return 更新数量
     */
    public int update(SysAdminDto sysAdminDto) {
        if (sysAdminDto != null && sysAdminDto.getId() != null) {
            try {
                SysAdmin sysAdmin = convertToSysAdmin(sysAdminDto);
                return sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
            } catch (SqlSessionException e) {
                log.error("用户更新异常 sysAdminService.update()", e);
            }
        }
        return 0;
    }

    public SysAdmin login(HttpServletRequest request, String adminName, String password, Long authCode) {
        // 从本系统查询用户
        SysAdmin sysAdmin = findByNameAndPwd(adminName, password);
        if (sysAdmin == null) {
            sysAdmin = new SysAdmin();
            // 用户名或密码错误
            sysAdmin.setRemark("1");
            return sysAdmin;
        }
        if (SysAdminEnums.DeleteFlagType.DEL.getValue().equals(sysAdmin.getDisableFlag())) {
            sysAdmin.setRoleId(null);
            // 用户被禁用
            sysAdmin.setRemark("2");
            return sysAdmin;
        }
        sysAdmin.setLastLoginTime(new Date());
        sysAdmin.setLastLoginIp(IpUtil.getClientIp(request));
        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
        return sysAdmin;
    }

    private SysAdmin convertToSysAdmin(SysAdminDto sysAdminDto) {
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setId(sysAdminDto.getId());
        sysAdmin.setUserName(sysAdminDto.getUserName());
        sysAdmin.setRoleId(sysAdminDto.getRoleId());
        sysAdmin.setRemark(sysAdminDto.getRemark());
        return sysAdmin;
    }

}
