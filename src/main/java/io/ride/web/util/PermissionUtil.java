package io.ride.web.util;

import io.ride.web.dao.*;
import io.ride.web.entity.*;
import io.ride.web.exception.HasNoPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-8
 * Time: 下午9:46
 */
public class PermissionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionUtil.class);

    private static UserInfo TEST_ADMIN = GET_TEST_ADMIN();               // 系统管理员
    private static UserInfo TEST_UNIT_ADMIN = GET_TEST_UNIT_ADMIN();     // 单位管理员
    private static UserInfo TEST_USER = GET_TEST_USER();                 // 普通用户

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");

    @Autowired
    private static RentDao rentDao = (RentDao) applicationContext.getBean("rentDao");

    @Autowired
    private static NodeDao nodeDao = (NodeDao) applicationContext.getBean("nodeDao");

    @Autowired
    private static UserInfoDao userInfoDao = (UserInfoDao) applicationContext.getBean("userInfoDao");

    @Autowired
    private static GetwayDao getwayDao = (GetwayDao) applicationContext.getBean("getwayDao");

    @Autowired
    private static UserAuthorDao userAuthorDao = (UserAuthorDao) applicationContext.getBean("userAuthorDao");


    /**
     * 判断当前是否有登录用户
     *
     * @param session session
     * @return 当前登录用户
     * @throws HasNoPermissionException 当前未登录
     */
    public static UserInfo isLogin(HttpSession session) throws HasNoPermissionException {
//        session.setAttribute("user", TEST_UNIT_ADMIN);
        UserInfo user = (UserInfo) session.getAttribute("user");
        LOGGER.info("isLogin() ---> user = {}", user);
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

    /**
     * 判断当前用户是否为普通用户
     *
     * @param currentUser 当前用户
     * @return boolean
     */
    public static boolean isUser(UserInfo currentUser) {
        return currentUser.getUserType() == 3;
    }

    /**
     * 返回非系统管理员
     *
     * @param currentUser 当前用户
     * @return boolean
     */
    public static boolean isNotAdmin(UserInfo currentUser) {
        return currentUser.getUserType() == 1 || currentUser.getUserType() == 2 || currentUser.getUserType() == 3;
    }

    /**
     * 根据用户类型和单位Id判断是否对网关又操作权限
     *
     * @param mark     网关mark
     * @param userType 用户类型
     * @param unitId   单位ID
     * @return boolean
     */
    public static boolean isRepairGetway(String mark, Integer userType, Integer unitId) {
        return userType == 0 || (userType == 1 || userType == 2) && rentDao.findByNodeMarkAndUnitId(mark, unitId) != null;
    }

    /**
     * 判断当前用户是否对节点是否又权限
     *
     * @param mark     节点mark
     * @param userType 用户类型
     * @param unitId   用户单位Id
     * @param username 用户名
     * @return boolean
     */
    public static boolean isAuthorNode(String mark, Integer userType, Integer unitId, String username) {
        if (userType == 0) {
            return true;
        }

        Node node = nodeDao.findByMark(mark, 1, unitId);
        Rent rent = null;
//        LOGGER.info("is author node node = {}", node);
        if (node != null) {
            Getway getway = getwayDao.findById(node.getGetwayId(), 1, unitId);
            if (getway == null) {
                return false;
            }
            rent = rentDao.findByNodeMarkAndUnitId(getway.getGetwayMark(), unitId);
//            LOGGER.info("is author rent  = {}", rent);

        }
        if (rent == null) return false;
        if (userType == 1 || userType == 2) {
            return true;
        }
//        LOGGER.info("当前为普通用户");
        if (userType == 3) {
            UserInfo user = userInfoDao.findByUsername(username, 2, unitId);
//            LOGGER.info("is author user info = {}", user);
            if (user == null) {
                return false;
            }
            boolean flag = userAuthorDao.isExists(user.getUserId(), node.getNodeId());
//            LOGGER.info("is author user info flag = {}", flag);

            return flag;
        }
        return false;
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
