package com.qijun.demo.mapper;

import com.qijun.demo.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * 角色Dao
 */
public interface RoleMapper {
    @Delete({
        "delete from role",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into role (id, role, ",
        "description)",
        "values (#{id,jdbcType=INTEGER}, #{role,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=true, resultType=Integer.class)
    int insert(Role record);

    @Select({
        "select",
        "id, role, description",
        "from role",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.qijun.demo.mapper.RoleMapper.BaseResultMap")
    Role selectByPrimaryKey(Integer id);



    @Update({
        "update role",
        "set role = #{role,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);

    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);
}