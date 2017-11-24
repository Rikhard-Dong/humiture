package io.ride.web.service;

import com.github.pagehelper.PageInfo;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:28
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码 未加密
     * @throws UsernameOrPasswordException 登录异常, 用户名或者密码不正确
     */
    UserInfo login(String username, String password)
            throws UsernameOrPasswordException;

    /**
     * 添加用户
     * 系统管理员可以添加任意单位的用户和用户类型
     * 单位管理员只能添加本用户的普通用户
     *
     * @param user    添加用户
     * @param session session
     * @throws IsExistsException        该用户已存在异常
     * @throws HasNoPermissionException 权限异常
     */
    void addUser(UserInfo user, HttpSession session)
            throws IsExistsException, HasNoPermissionException, PasswordNotEqualsException;

    /**
     * 更新用户
     *
     * @param userInfo
     * @param session
     * @throws NotFoundException
     * @throws HasNoPermissionException
     */
    void updateUser(UserInfo userInfo, HttpSession session)
            throws NotFoundException, HasNoPermissionException, PasswordNotEqualsException;

    /**
     * 根据用户名删除用户Id
     *
     * @param username
     * @param session
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    void deleteUser(String username, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    /**
     * 根据用户Id查找
     *
     * @param id
     * @param session
     * @return
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    UserInfo findUser(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    /**
     * 根据用户名查找
     *
     * @param username
     * @param session
     * @return
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    UserInfo findUser(String username, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    /**
     * 显示用户列表
     * 系统管理员查看所有用户信息
     * 单位管理员只能查看该单位所属用户
     *
     * @param session sessuib
     * @return 用户列表
     * @throws HasNoPermissionException 权限异常
     */
    List<UserInfo> listUser(HttpSession session)
            throws HasNoPermissionException;

    List<String> listUsernames(HttpSession session)
            throws HasNoPermissionException;
}
