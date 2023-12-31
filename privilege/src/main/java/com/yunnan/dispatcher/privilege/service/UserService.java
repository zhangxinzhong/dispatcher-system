package com.yunnan.dispatcher.privilege.service;

import com.github.pagehelper.Page;
import com.yunnan.dispatcher.privilege.condition.UserQueryCondition;
import com.yunnan.dispatcher.privilege.model.LoginUserInfo;
import com.yunnan.dispatcher.privilege.model.User;

import java.util.Optional;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-20 上午9:01
 */
public interface UserService {
    /**
     * 通过userId 获取用户信息
     * @param id
     * @return
     */
    Optional<User> getUserById(Integer id);

    /**
     * 通过loginNo loginUserInfo
     * @param loginNo
     * @return
     */
    Optional<LoginUserInfo> getLoginUserInfo(String loginNo);

    /**
     * 查询所有用户列表
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    Page<User> getAllUsersPages(Integer page,Integer limit);

    /**
     * 创建用户
     * @param user 用户
     */
    void createUser(User user);

    /**
     * 修改用户
     * @param user 用户
     */
    void editUser(User user);

    /**
     * 分页查询所有用户
     * @param userQueryCondition
     * @param page 当前页
     * @param limit 每页条数
     * @return
     */
    Page<User> queryAll(UserQueryCondition userQueryCondition, Integer page, Integer limit);

    /**
     * 删除用户
     * @param userId 用户编号
     */
    void deleteUser(Integer userId);


    /**
     * 判断用户是否可删除
     * @param userId
     * @return
     */
    Boolean findRoleUserByUserId(Integer userId);

    /**
     * 重置用户密码
     * @param userId
     */
    void resetPassword(Integer userId);


    /**
     * 通过用户id修改密码
     * @param userId
     * @param password
     */
    void updateUserPwdByUserId(Integer userId,String password);
}
