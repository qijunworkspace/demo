package com.qijun.demo.mapper.provider;

import com.google.common.base.Joiner;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于拼装结构化SQL语句
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
public class RoleProvider {

    static final String PARAM_ROLE_ID = "roleId";

    static final String PARAM_PERMISSION_IDS = "permissionIds";

    /**
     * 删除角色与权限的对应关系
     * @param params 角色id、权限id
     * @return SQL语句
     */
    public String delRolePermissions(Map<String, Object> params){
        //获取角色编号
        Integer roleId = (Integer) params.get(PARAM_ROLE_ID);
        //获取删除的权限列表，为NULL表示全部删除
        List<Integer> permissionIds = null;
        if (null != params.get(PARAM_PERMISSION_IDS)){
            permissionIds = (List<Integer>) params.get(PARAM_PERMISSION_IDS);
        }

        //返回拼装SQL语句
        SQL sql = new SQL();
        sql.DELETE_FROM("role_permission");
        sql.WHERE("role_id="+roleId);
        if (null != permissionIds){
            sql.WHERE("permission_id IN ("+Joiner.on(",").skipNulls().join(permissionIds)+")");
        }
        return sql.toString();
    }

    /**
     * 插入用户权限信息
     * @param params 角色Id、权限Id
     * @return SQL语句
     */
    public String insertRolePermissions(Map<String, Object> params){

        //获取角色编号
        Integer roleId = (Integer) params.get(PARAM_ROLE_ID);
        //获取插入的权限列表，不能为空
        List<Integer> permissionIds = (List<Integer>) params.get(PARAM_PERMISSION_IDS);

        //拼接语句
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO role_permission ");
        stringBuilder.append("(role_id, permission_id) ");
        stringBuilder.append("VALUES ");

        List<String> values = new ArrayList<>();
        for (Integer permissionId : permissionIds){
            values.add("("+roleId+","+permissionId+")");
        }
        stringBuilder.append(Joiner.on(",").skipNulls().join(values));

        return stringBuilder.toString();
    }
}