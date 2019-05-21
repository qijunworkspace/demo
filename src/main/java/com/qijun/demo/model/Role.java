package com.qijun.demo.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 角色表
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
public class Role implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色
     */
    @NotBlank(message = "Role name cannot be blank")
    private String role;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限列表
     */
    private List<Permission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }


    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()+"["+getClass().getClassLoader()+"]");
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", role=").append(role);
        sb.append(", description=").append(description);
        //sb.append(", permissions=").append(permissions);
        sb.append("]");
        return sb.toString();
    }
}