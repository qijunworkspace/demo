package com.qijun.demo.mapper;

import com.qijun.demo.mapper.provider.RoleProvider;
import com.qijun.demo.model.Permission;
import com.qijun.demo.model.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色Dao
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
@Repository
public interface RoleMapper {

    /**
     * 获取所有的角色信息
     * @return 角色信息
     */
    @Select("SELECT * FROM role")
    @Results(id="RoleResult", value = {
            @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "role", property = "role", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "permission", javaType = List.class,
                    many = @Many(select = "getRolePermissions", fetchType = FetchType.LAZY))
    })
    List<Role> getAll();


    /**
     * 获取角色拥有的权限信息
     * @param roleId 角色主键
     * @return 权限列表
     */
    @Select("SELECT * FROM permission WHERE id IN (SELECT * FROM role_permission WHERE role_id = #{roleId, jdbcType=INTEGER})")
    @ResultMap("com.qijun.demo.mapper.PermissionMapper.PermissionResult")
    List<Permission> getRolePermissions(Integer roleId);


    /**
     * 删除角色对应的权限信息
     * @param roleId 角色主键
     * @param permissionIds 权限主键列表
     * @return 删除个数
     */
    @DeleteProvider(type = RoleProvider.class, method = "delRolePermissions")
    int delRolePermissions(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);


    /**
     * 插入用户角色信息
     * @param roleId 角色主键
     * @param permissionIds 权限主键列表，不能为空
     * @return 插入个数
     */
    @InsertProvider(type = RoleProvider.class, method = "insertRolePermissions")
    int insertRolePermissions(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);

    /**
     * 删除角色
     * @param id 角色主键
     * @return 删除个数
     */
    @Delete({
        "delete from role",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入角色
     * @param record 角色记录
     * @return 插入个数
     */
    @Insert({
        "insert into role (id, role, ",
        "description)",
        "values (#{id,jdbcType=INTEGER}, #{role,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=true, resultType=Integer.class)
    int insert(Role record);

    /**
     * 选择角色
     * @param id 角色主键
     * @return 角色信息
     */
    @Select({
        "select",
        "id, role, description",
        "from role",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("RoleResult")
    Role selectByPrimaryKey(Integer id);


    /**
     * 更新角色信息
     * @param record
     * @return
     */
    @Update({
        "update role",
        "set role = #{role,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);

    /**
     * 插入非NULL的信息
     * @param record  角色记录
     * @return 插入个数
     */
    int insertSelective(Role record);

    /**
     * 更新非NULL的信息
     * @param record 角色记录
     * @return 插入个数
     */
    int updateByPrimaryKeySelective(Role record);
}