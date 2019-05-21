package com.qijun.demo.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 权限表
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
public class Permission implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限名
     */
    @NotBlank(message = "Permission name cannot be blank")
    private String permission;

    /**
     * 权限类型： 0-menu 1-method
     */
    private Integer type;

    /**
     * 描述信息
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", permission=").append(permission);
        sb.append(", type=").append(type);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}