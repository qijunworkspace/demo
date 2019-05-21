package com.qijun.demo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qijun.demo.model.Role;
import com.qijun.demo.model.User;
import com.qijun.demo.model.vo.PageRequest;
import com.qijun.demo.response.CustomException;
import com.qijun.demo.response.CustomResponse;
import com.qijun.demo.response.DataTablePage;
import com.qijun.demo.service.RoleService;
import com.qijun.demo.service.UserService;
import com.qijun.demo.util.ResponseUtil;
import com.qijun.demo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 *
 * @author Qijun
 * @version 1.0
 * @date 5/1/2019 16:12
 */
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService){
        this.roleService = roleService;
        this.userService = userService;
    }


    /**
     * 获取所有的角色列表
     * @return
     */
    @PostMapping("/getRoleList")
    public CustomResponse getRoleList(){
        List<Role> roles = roleService.findAll();
        return ResponseUtil.success(roles);
    }

    /**
     * 获取分页的用户信息
     * @return 分页信息及数据
     */
    @PostMapping("/searchUser")
    public CustomResponse searchUser(@ModelAttribute @Validated PageRequest pageInfo,
                                     @RequestParam String searchName, @RequestParam Integer searchRole){

        PageHelper.startPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        Page<User> page = PageHelper.startPage(pageInfo.getPageIndex(), pageInfo.getPageSize())
                .doSelectPage(()-> userService.searchUser(searchName, searchRole));
        return ResponseUtil.success(new DataTablePage<>(page, pageInfo.getDraw()));
    }


    /**
     * 获取id对应的用户信息
     * @param userId 用户主键
     * @return 用户信息
     */
    @PostMapping("/getUserInfo")
    public CustomResponse getUserInfo(@RequestParam @NotNull Integer userId){
        return ResponseUtil.success(userService.findById(userId));
    }

    /**
     * 审核用户申请
     * @param userId 用户主键
     * @param roleId 角色编号
     * @param status 账户状态
     * @return 审核结果
     */
    @PostMapping("/reviewUser")
    @Transactional
    public CustomResponse reviewUser(@RequestParam @NotNull Integer userId, @RequestParam @NotNull Integer roleId,
                                     @RequestParam @NotNull Integer status){

        User user = userService.findById(userId);
        if (null != user){
            user.setStatus(status);
            userService.update(user);
            userService.updateUserRole(userId, roleId);
            return ResponseUtil.success("User info is updated.");
        }else{
            return ResponseUtil.error("User info does not exist!");
        }
    }


    /**
     * 修改用户信息
     * @param userId 主键
     * @param password 密码
     * @param roleId 角色ID
     * @param status 状态
     * @return
     */
    @PostMapping("/modifyUser")
    @Transactional
    public CustomResponse modifyUser(@RequestParam @NotNull Integer userId, @RequestParam @NotEmpty String password,
                                     @RequestParam @NotNull Integer roleId,
                                     @RequestParam @NotNull Integer status){

        User user = userService.findById(userId);
        if (null != user){
            //判断密码是否更改
            if (!password.equals(user.getPassword())){
                user.setPassword(SecurityUtil.encryptAES(password.getBytes()));
            }
            user.setStatus(status);
            userService.update(user);
            userService.updateUserRole(userId, roleId);
            return ResponseUtil.success("User info is updated.");
        }else{
            return ResponseUtil.error("User info does not exist!");
        }
    }


    /**
     * 管理员添加新用户
     * @param roleId 角色编号
     * @param user 用户信息
     * @return 添加结果
     */
    @PostMapping("/addUser")
    @Transactional
    public CustomResponse addUser(@RequestParam @NotNull Integer roleId, @ModelAttribute User user){
        //判断用户名是否存在
        if (null != userService.findByUsername(user.getUsername())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        //补充用户信息，默认状态为正常
        user.setPassword(SecurityUtil.encryptAES(user.getPassword().getBytes()));
        user.setStatus(1);
        user.setRegistTime(new Date());

        //保存对象
        userService.insert(user);
        userService.updateUserRole(user.getId(), roleId);
        return ResponseUtil.success("Add user["+user.getUsername()+"] successed!");
    }

    /**
     * 获取登陆用户的信息
     * @return 登陆用户信息
     */
    @PostMapping("/getcurrentUser")
    public CustomResponse getcurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if (null != user){
            return ResponseUtil.success(user);
        }
        throw new CustomException(HttpStatus.FORBIDDEN, "Current user does not exist anymore!");
    }

    /**
     * 更新当前用户信息
     * @param user 修改的信息
     * @return 更新结果
     */
    @PostMapping("/updateCurrentUser")
    public CustomResponse updateCurrentUser(@ModelAttribute User user){
        User currentUser = userService.findById(user.getId());
        if (null != currentUser){

            currentUser.setEmail(user.getEmail());
            currentUser.setTelephone(user.getTelephone());
            if (!currentUser.getPassword().equals(user.getPassword())){
                currentUser.setPassword(SecurityUtil.encryptAES(user.getPassword().getBytes()));
            }
            userService.update(currentUser);
            return ResponseUtil.success("Update Successfully!");
        }
        throw new CustomException(HttpStatus.FORBIDDEN, "Current user does not exist anymore!");
    }
}
