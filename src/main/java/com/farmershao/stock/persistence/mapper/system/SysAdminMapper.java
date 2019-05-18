package com.farmershao.stock.persistence.mapper.system;

import com.farmershao.stock.dto.SysAdminDto;
import com.farmershao.stock.persistence.model.system.SysAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysAdminMapper
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
public interface SysAdminMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAdmin record);

    /**
     * 后台查询系统用户列表
     * @param search 查询dto
     * @return 系统用户列表
     */
    List<SysAdmin> selectList(@Param("search")SysAdminDto search);

    /**
     * 后台查询系统用户
     * @param name 用户名
     * @return 系统用户
     */
    SysAdmin findByName(@Param("name")String name);

    SysAdmin findByNameAndPwd(@Param("name")String name, @Param("pwd")String pwd);

    /**
     * 根据主键更新用户密码
     * @param record 系统用户
     * @return row
     */
    int updatePwdByPrimaryKey(@Param("record") SysAdmin record);
}