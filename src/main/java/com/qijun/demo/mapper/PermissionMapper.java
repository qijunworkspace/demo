package com.qijun.demo.mapper;

import com.qijun.demo.model.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * 权限Dao
 */
public interface PermissionMapper {
    @Delete({
        "delete from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into permission (id, permission, ",
        "type, description)",
        "values (#{id,jdbcType=INTEGER}, #{permission,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{description,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=true, resultType=Integer.class)
    int insert(Permission record);

    int insertSelective(Permission record);

    @Select({
        "select",
        "id, permission, type, description",
        "from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.qijun.demo.mapper.PermissionMapper.BaseResultMap")
    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    @Update({
        "update permission",
        "set permission = #{permission,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Permission record);
}