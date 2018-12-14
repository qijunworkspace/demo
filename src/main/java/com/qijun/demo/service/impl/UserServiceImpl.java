package com.qijun.demo.service.impl;

import com.qijun.demo.mapper.UserMapper;
import com.qijun.demo.model.User;
import com.qijun.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户服务实现类
 * @author Qijun
 * @date 12/14/18 1:40 PM
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Override
    public User findByUsername(String username) {
        if (!StringUtils.isEmpty(username)){
            return userMapper.findByUserName(username);
        }
        return null;
    }

    @Override
    public User findById(Integer id) {
        if (null != id){
            return userMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserRole(Integer userId, Integer roleId) {
        if (null != userId){
            userMapper.delUserRole(userId);
            if (null != roleId){
                return userMapper.insertUserRole(userId, roleId);
            }
        }
        return 0;
    }

    @Override
    public List<User> findAll() {
        return userMapper.getAll();
    }

    @Override
    public User get(Integer id) {
        if (null != id){
            return userMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public int insert(User record) {
        if (null != record){
            return userMapper.insert(record);
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        if (null != id){
            return userMapper.deleteByPrimaryKey(id);
        }
        return 0;
    }

    @Override
    public int update(User record) {
        if (null != record && null != record.getId()){
            return userMapper.updateByPrimaryKey(record);
        }
        return 0;
    }

    @Override
    public int updateSelective(User record) {
        if (null != record && null != record.getId()){
            return userMapper.updateByPrimaryKeySelective(record);
        }
        return 0;
    }
}
