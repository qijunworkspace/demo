package com.qijun.demo.service.impl;

import com.qijun.demo.mapper.RoleMapper;
import com.qijun.demo.model.Role;
import com.qijun.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * role方法实现类
 *
 * @author Qijun
 * @version 1.0
 * @date 5/3/2019 18:44
 */
@Service
public class RoleServiceImpl implements RoleService {

    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }


    @Override
    public List<Role> findAll() {
        return roleMapper.getAll();
    }

    @Override
    public Role get(Integer id) {
        if (null != id){
            return roleMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public int insert(Role record) {
        if (null != record){
            return roleMapper.insert(record);
        }
        return -1;
    }

    @Override
    public int delete(Integer id) {
        if (null != id){
            return roleMapper.deleteByPrimaryKey(id);
        }
        return -1;
    }

    @Override
    public int update(Role record) {
        if (null != record && null != record.getId()){
            return roleMapper.updateByPrimaryKey(record);
        }
        return -1;
    }

    @Override
    public int updateSelective(Role record) {
        if (null != record && null != record.getId()){
            return roleMapper.updateByPrimaryKeySelective(record);
        }
        return -1;
    }
}
