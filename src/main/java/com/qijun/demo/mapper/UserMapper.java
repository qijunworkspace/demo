package com.qijun.demo.mapper;

import com.qijun.demo.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Dao
 */
public interface UserMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user (id, username, ",
        "password, email, ",
        "telephone, portrait, ",
        "status, regist_time)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{telephone,jdbcType=VARCHAR}, #{portrait,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{registTime,jdbcType=DATE})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=true, resultType=Integer.class)
    int insert(User record);

    int insertSelective(User record);

    @Select({
        "select",
        "id, username, password, email, telephone, portrait, status, regist_time",
        "from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.qijun.demo.mapper.UserMapper.BaseResultMap")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "telephone = #{telephone,jdbcType=VARCHAR},",
          "portrait = #{portrait,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "regist_time = #{registTime,jdbcType=DATE}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);
}