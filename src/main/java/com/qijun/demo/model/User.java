package com.qijun.demo.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * @author Qijun
 * @date 12/14/18 9:45 AM
 * @version 1.0
 */
public class User implements UserDetails {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "Username cannot be blank")
    @Length(min = 5, max = 20, message = "Username's length should between 5 and 20")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "Password cannot be blank")
    @Length(min = 5, max = 20, message = "Password's length should between 5 and 20")
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "Email address is incorrect")
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 头像地址
     */
    private String portrait;

    /**
     * 状态 0-未审核， 1-正常， 2-已锁定， 3-已删除/已过期
     */
    private Integer status;
    /**
     * 注册时间
     */
    private Date registTime;

    /**
     * 用户对应的角色
     */
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return status != 3;
    }


    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return status != 2;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return status == 1;
    }


    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        if (null != role){
            auth.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return auth;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", telephone=").append(telephone);
        sb.append(", portrait=").append(portrait);
        sb.append(", status=").append(status);
        sb.append(", registTime=").append(registTime);
        sb.append(", role=").append(role);
        sb.append("]");
        return sb.toString();
    }
}