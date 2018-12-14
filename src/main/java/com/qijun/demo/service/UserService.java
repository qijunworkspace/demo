package com.qijun.demo.service;

import com.qijun.demo.model.User;

/**
 * 用户服务接口
 * @author Qijun
 * @date ate 11/28/18 11:36 AM
 * @version 1.0
 */
public interface UserService extends BaseService<User, Integer>{

    /**
     * 通过用户名获取
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 通过主键获取
     * @param id 主键
     * @return 用户对象
     */
    User findById(Integer id);

    /**
     * 更新用户的角色信息
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 更新个数
     */
    int updateUserRole(Integer userId, Integer roleId);
}
