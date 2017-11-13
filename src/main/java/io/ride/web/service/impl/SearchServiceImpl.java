package io.ride.web.service.impl;

import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.NodeDao;
import io.ride.web.dao.UnitDao;
import io.ride.web.dao.UserInfoDao;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.SearchService;
import io.ride.web.util.PermissionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午7:07
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {
    private static Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);


    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private GetwayDao getwayDao;

    @Autowired
    private NodeDao nodeDao;


    public List<UserInfo> searchUser(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        List<UserInfo> users;
        if (PermissionUnit.isAdmin(currentUser)) {
            // 系统管理员返回全部用户信息
        } else if (PermissionUnit.isUnitAdmin(currentUser)) {
            // 单位管理员返回该单位用户
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        users = userInfoDao.search(arg);

        return users;
    }

    public List<Unit> searchUnit(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        List<Unit> units;
        if (PermissionUnit.isAdmin(currentUser)) {
            // 系统管理员返回全部用户信息
        } else if (PermissionUnit.isUnitAdmin(currentUser)) {
            // 单位管理员返回该单位用户
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        units = unitDao.search(arg);
        return units;
    }

    public List<Getway> searchGetway(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        return null;
    }

    public List<Node> searchNode(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        return null;
    }
}
