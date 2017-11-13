package io.ride.web.util;

import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-8
 * Time: 下午9:46
 */
public class PermissionUnit {
    /**
     * 判断当前是否有登录用户
     *
     * @param session session
     * @return 当前登录用户
     * @throws HasNoPermissionException 当前未登录
     */
    public static UserInfo isLogin(HttpSession session) throws HasNoPermissionException {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            user = new UserInfo();
            user.setUserType(0);
//            throw new HasNoPermissionException("登录后操作!");
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
}
