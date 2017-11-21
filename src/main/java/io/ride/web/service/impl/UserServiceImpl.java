package io.ride.web.service.impl;

import io.ride.web.dao.UnitDao;
import io.ride.web.dao.UserInfoDao;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.*;
import io.ride.web.service.UserService;
import io.ride.web.util.MyDateFormat;
import io.ride.web.util.PermissionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:29
 * <p>
 * 处理关于用户的操作
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoDao userInfoDao;


    @Autowired
    private UnitDao unitDao;

    public UserInfo login(String username, String password) throws UsernameOrPasswordException {
        if (!userInfoDao.isAccountExists(username)) {
            LOGGER.error("用户名或者密码错误!登录失败");
            throw new UsernameOrPasswordException("用户名或者密码错误!");
        }
        UserInfo user = userInfoDao.accountValidate(username, password);
        if (user == null) {
            LOGGER.error("用户名或者密码错误!登录失败");
            throw new UsernameOrPasswordException("用户名或者密码错误!");
        }
        if (!PermissionUtil.isAdmin(user)) {
            Unit unit = unitDao.findById(user.getUnitId());
            user.setUnit(unit);
        }
        LOGGER.info("{}在{}登录本系统!", username, MyDateFormat.format(new Date()));
        return user;
    }

    public void addUser(UserInfo user, HttpSession session)
            throws IsExistsException, HasNoPermissionException, PasswordNotEqualsException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        user.setPassword(user.getPassword());
        Unit unit = unitDao.findByTitle(user.getTitle());
        if (unit == null) {
            throw new NotFoundException("单位不存在");
        }
        user.setUnitId(unit.getUnitId());
        LOGGER.info("添加用户 = {}", user);
        int result = 0;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            if (userInfoDao.isAccountExists(user.getUsername())) {
                throw new IsExistsException("添加用户失败, 因为该用户已经存在");
            }
            if (PermissionUtil.isAdmin(currentUser)) {
                result = userInfoDao.addUser(user);
            } else if (PermissionUtil.isUnitAdmin(currentUser)) {
                // 单位管理员只能新建本单位的普通用户
                user.setUserType(3);
                user.setUnitId(currentUser.getUnitId());
                result = userInfoDao.addUser(user);
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (result == 0) {
            throw new UpdateException("添加用户数据失败");
        }

    }

    /**
     * 更新用户
     *
     * @param user    添加用户
     * @param session session
     * @throws NotFoundException          .
     * @throws HasNoPermissionException   .
     * @throws PasswordNotEqualsException .
     */
    public void updateUser(UserInfo user, HttpSession session)
            throws NotFoundException, HasNoPermissionException, PasswordNotEqualsException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
//        if (!user.getPassword().equals(password2)) {
//            throw new PasswordNotEqualsException("密码不一致");
//        }
//        user.setPassword(MD5Encrypt.encrypt(user.getPassword()));
        LOGGER.info("添加用户 = {}", user);
        int result = 0;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            if (userInfoDao.findByUsername(user.getUsername(), currentUser.getUserType(), currentUser.getUnitId()) == null) {
                throw new NotFoundException("更新用户失败, 因为用户不存在");
            }
            if (PermissionUtil.isAdmin(currentUser)) {
                result = userInfoDao.updateByUsername(user);
            } else if (PermissionUtil.isUnitAdmin(currentUser)) {
                if (userInfoDao.findFromUnitWithUsername(user.getUsername(), currentUser.getUnitId()) != null) {
                    // 如果是单位管理, 则不能修改用户身份以及用户所属单位
                    UserInfo oldUser = userInfoDao.findByUsername(user.getUsername(),
                            currentUser.getUserType(), currentUser.getUnitId());
                    user.setUnitId(oldUser.getUnitId());
                    user.setUserType(oldUser.getUserType());
                    result = userInfoDao.updateByUsername(user);
                } else {
                    throw new HasNoPermissionException("单位管理员只能更新本单位用户");
                }
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (result == 0) {
            throw new UpdateException("跟新用户信息失败");
        }

    }

    public void deleteUser(String username, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result = 0;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            if (userInfoDao.findByUsername(username, currentUser.getUserType(), currentUser.getUnitId()) == null) {
                throw new NotFoundException("删除用户失败, 因为用户不存在");
            }
            if (username.equals(currentUser.getUsername())) {
                throw new HasNoPermissionException("不能删除自身!");
            }
            if (PermissionUtil.isUnitAdmin(currentUser)) {
                // 单位管理员只能删除本单位用户
                if (userInfoDao.findFromUnitWithUsername(username, currentUser.getUnitId()) != null) {
                    result = userInfoDao.deleteByUsername(username);
                } else {
                    throw new HasNoPermissionException("单位管理员只能删除本单位用户");
                }
            } else if (PermissionUtil.isAdmin(currentUser)) {
                result = userInfoDao.deleteByUsername(username);
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (result == 0) {
            throw new UpdateException("数据更新异常");
        }

    }

    public UserInfo findUser(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        UserInfo user = null;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            if (userInfoDao.findByUserId(id) == null) {
                throw new NotFoundException("查找用户失败, 因为用户不存在");
            }
            if (PermissionUtil.isUnitAdmin(currentUser)) {
                user = userInfoDao.findFromUnitWithUserId(id, currentUser.getUnitId());
            } else if (PermissionUtil.isAdmin(currentUser)) {
                user = userInfoDao.findByUserId(id);
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (user == null) {
            throw new NotFoundException("查找用户失败");
        }
        return user;
    }

    public UserInfo findUser(String username, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        UserInfo user = null;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            if (userInfoDao.findByUsername(username, currentUser.getUserType(), currentUser.getUnitId()) == null) {
                throw new NotFoundException("查找用户失败, 因为用户不存在");
            }
            if (PermissionUtil.isUnitAdmin(currentUser)) {
                user = userInfoDao.findFromUnitWithUsername(username, currentUser.getUnitId());
            } else if (PermissionUtil.isAdmin(currentUser)) {
                user = userInfoDao.findByUsername(username, currentUser.getUserType(), currentUser.getUnitId());
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (user == null) {
            throw new NotFoundException("查找用户失败");
        }
        return user;
    }

    /**
     * 列出所有用户 不同用户类型显示内容不一致
     *
     * @param session session
     * @return 返回用户列表
     * @throws HasNoPermissionException 权限不足
     */
    public List<UserInfo> listUser(HttpSession session)
            throws HasNoPermissionException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        List<UserInfo> users;
        if (PermissionUtil.isAdmin(currentUser)) {
            // 系统管理员返回全部用户信息
            users = userInfoDao.list();
        } else if (PermissionUtil.isUnitAdmin(currentUser)) {
            // 单位管理员返回该单位用户
            users = userInfoDao.listFromUnit(currentUser.getUnitId());
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        if (users != null) {
            for (UserInfo user : users) {
                user.setUnit(unitDao.findById(user.getUnitId()));
                user.setTitle(user.getUnit().getTitle());
            }
        }
        return users;
    }
}
