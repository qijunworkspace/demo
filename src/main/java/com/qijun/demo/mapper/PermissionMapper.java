package com.qijun.demo.mapper;

import com.qijun.demo.model.Permission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限Dao
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
@Repository
public interface PermissionMapper {

    /**
     * 获取所有权限信息
     * @return 权限列表
     */
    @Select("SELECT * FROM permission")
    @Results(id = "PermissionResult", value = {
            @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "permission", property = "permission", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
    })
    List<Permission> getAll();

    /**
     * 删除权限
     * @param id 权限主键
     * @return 删除个数
     */
    @Delete({
        "delete from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入权限
     * @param record 权限记录
     * @return 插入个数
     */
    @Insert({
        "insert into permission (id, permission, ",
        "type, description)",
        "values (#{id,jdbcType=INTEGER}, #{permission,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{description,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Permission record);

    /**
     * 获取权限
     * @param id 权限主键
     * @return 权限记录
     */
    @Select({
        "select",
        "id, permission, type, description",
        "from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("PermissionResult")
    Permission selectByPrimaryKey(Integer id);


    /**
     * 更新权限信息
     * @param record 权限记录
     * @return 更新个数
     */
    @Update({
        "update permission",
        "set permission = #{permission,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Permission record);


    /**
     * 选择性插入
     * @param record 权限记录
     * @return 插入个数
     */
    int insertSelective(Permission record);

    /**
     * 选择性更新
     * @param record 权限记录
     * @return 更新个数
     */
    int updateByPrimaryKeySelective(Permission record);
}