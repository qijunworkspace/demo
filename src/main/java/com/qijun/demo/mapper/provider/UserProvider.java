package com.qijun.demo.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * User表查询方法
 *
 * @author Qijun
 * @version 1.0
 * @date 5/3/2019 10:32
 */
public class UserProvider {

    static final String PARAM_USERNAME = "username";

    /**
     * 拼接查找用户的语句
     * @param params 用户名，角色ID
     * @return SQL语句
     */
    public String searchUser(Map<String, Object> params){
        //获取模糊用户名
        String username = (null==params.get(PARAM_USERNAME)?
                null:  (String) params.get(PARAM_USERNAME));

        //获取角色编号
        Integer roleId = (null==params.get(RoleProvider.PARAM_ROLE_ID)?
                null: (Integer) params.get(RoleProvider.PARAM_ROLE_ID));

        //拼接语句
        SQL sql = new SQL();
        sql.SELECT("u.*");
        sql.FROM("user u");
        sql.LEFT_OUTER_JOIN("user_role ur on u.id = ur.user_id");
        if (!StringUtils.isEmpty(username)){
            sql.WHERE("username like CONCAT('%', '"+username+"' ,'%')");
        }
        if (null != roleId){
            sql.WHERE("ur.role_id = "+roleId);
        }
        sql.ORDER_BY("u.status ASC, u.username ASC");

        return sql.toString();
    }

}
