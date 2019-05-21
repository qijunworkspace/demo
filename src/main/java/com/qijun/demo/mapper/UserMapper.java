package com.qijun.demo.mapper;

import com.qijun.demo.mapper.provider.UserProvider;
import com.qijun.demo.model.Role;
import com.qijun.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Dao
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
@Repository
public interface UserMapper {

    /**
     * 获取所有用户信息
     * @return 用户信息列表
     */
    @Select("SELECT * FROM user")
    @Results(id="UserResult", value = {
            @Result(id=true, column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "telephone", property = "telephone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "portrait", property = "portrait", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "regist_time", property = "registTime", jdbcType = JdbcType.DATE),
            @Result(column = "id", property = "role", one = @One(select = "getUserRole", fetchType = FetchType.EAGER))
    })
    List<User> getAll();

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    @ResultMap("UserResult")
    User findByUserName(String username);

    /**
     * 获取用户对应的角色信息
     * @param userId 用户主键
     * @return 角色信息
     */
    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId}) LIMIT 1")
    //@ResultMap("com.qijun.demo.mapper.RoleMapper.RoleResult")
    Role getUserRole(Integer userId);

    /**
     * 删除用户角色
     * @param userId 用户主键
     * @return 删除个数
     */
    @Delete("DELETE FROM user_role WHERE user_id = #{userId}")
    int delUserRole(Integer userId);

    /**
     * 插入用户角色
     * @param roleId 角色主键
     * @param userId 用户主键
     * @return 插入个数
     */
    @Insert({
            "<script>",
            "INSERT INTO user_role (role_id, user_id)",
            "VALUES",
            "(#{roleId}, #{userId})",
            "</script>"
    })
    int insertUserRole(@Param("userId")  Integer userId, @Param("roleId") Integer roleId);

    /**
     * 删除用户
     * @param id 用户主键
     * @return 删除个数
     */
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入用户
     * @param record 用户对象
     * @return 插入个数
     */
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
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before = false, resultType=Integer.class)
    int insert(User record);

    /**
     * 通过主键查找用户
     * @param id 用户主键
     * @return 用户对象
     */
    @Select({
        "select",
        "id, username, password, email, telephone, portrait, status, regist_time",
        "from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.qijun.demo.mapper.UserMapper.UserResult")
    User selectByPrimaryKey(Integer id);

    /**
     * 通过主键更新用户信息
     * @param record 用户记录
     * @return 更新个数
     */
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

    /**
     * 选择性(非NULL值)插入记录
     * @param record 用户部分记录
     * @return 插入个数
     */
    int insertSelective(User record);

    /**
     * 选择性(非NULL值)更新记录
     * @param record 用户部分记录
     * @return 更新个数
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 搜索用户
     * @param username 用户名
     * @param roleId 角色编号
     * @return
     */
    @SelectProvider(type = UserProvider.class, method="searchUser")
    @ResultMap("UserResult")
    List<User> searchUser(@Param("username") String username, @Param("roleId")  Integer roleId);
}