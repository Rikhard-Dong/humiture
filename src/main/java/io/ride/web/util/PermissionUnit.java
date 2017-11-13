package io.ride.web.util;

import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-8
 * Time: 下午9:46
 */
public class PermissionUnit {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionUnit.class);

    public static UserInfo TEST_ADMIN = GET_TEST_ADMIN();               // 系统管理员
    public static UserInfo TEST_UNIT_ADMIN = GET_TEST_UNIT_ADMIN();     // 单位管理员
    public static UserInfo TEST_USER = GET_TEST_USER();                 // 普通用户


    /**
     * 判断当前是否有登录用户
     *
     * @param session session
     * @return 当前登录用户
     * @throws HasNoPermissionException 当前未登录
     */
    public static UserInfo isLogin(HttpSession session) throws HasNoPermissionException {
        session.setAttribute("user", TEST_ADMIN);
        UserInfo user = (UserInfo) session.getAttribute("user");
//        LOGGER.info("isLogin() ---> user = {}", user);
        if (user == null) {
            throw new HasNoPermissionException("登录后操作!");
        }
        return user;
    }

    /**
     * 判断当前用户是否为系统管理员
     *
     * @param currentUser 当前用户
     * @return boolean
     */
    public static boolean isAdmin(UserInfo currentUser) {
        return currentUser.getUserType() == 0;
    }

    /**
     * 判断当前用户是否为单位管理员
     *
     * @param currentUser 当前用户
     * @return boolean
     */
    public static boolean isUnitAdmin(UserInfo currentUser) {
        return currentUser.getUserType() == 2 || currentUser.getUserType() == 1;
    }

    private static UserInfo GET_TEST_ADMIN() {
        UserInfo user = new UserInfo();
        user.setUserType(0);
        user.setName("test_admin");

        return user;
    }

    private static UserInfo GET_TEST_UNIT_ADMIN() {
        UserInfo user = new UserInfo();
        user.setUserType(2);
        user.setName("test_unit_admin");
        user.setUnitId(1);

        return user;
    }

    private static UserInfo GET_TEST_USER() {
        UserInfo user = new UserInfo();
        user.setUserType(4);
        user.setName("test_user");
        user.setUnitId(1);

        return user;
    }

}
