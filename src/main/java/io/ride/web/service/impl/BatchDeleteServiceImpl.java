package io.ride.web.service.impl;

import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.NodeDao;
import io.ride.web.dao.UnitDao;
import io.ride.web.dao.UserInfoDao;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.BatchDeleteService;
import io.ride.web.util.ParamDivisionUtil;
import io.ride.web.util.PermissionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午6:34
 */
@Service("batchDeleteService")
public class BatchDeleteServiceImpl implements BatchDeleteService {
    private static Logger LOGGER = LoggerFactory.getLogger(BatchDeleteServiceImpl.class);

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private GetwayDao getwayDao;

    @Autowired
    private NodeDao nodeDao;


    @Transactional
    public void batchDeleteUser(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        int result;
        if (PermissionUnit.isAdmin(currentUser) || PermissionUnit.isUnitAdmin(currentUser)) {
            String[] usernames = ParamDivisionUtil.getParams(arg);

            for (String username : usernames) {
                if (username.equals(currentUser.getUsername())) {
                    throw new HasNoPermissionException("权限错误! 不能删除自身!");
                }

                if (currentUser.getUserType() == 1 || currentUser.getUserType() == 2) {
                    if (userInfoDao.findFromUnitWithUsername(username, currentUser.getUnitId()) == null) {
                        throw new HasNoPermissionException("用户删除失败{}, 本单位不存在该用户");
                    }
                }
                if (userInfoDao.findByUsername(username) == null) {
                    throw new NotFoundException("删除用户" + username + "失败, 因为该用户不存在");
                }
                result = userInfoDao.deleteByUsername(username);
                if (result == 0) {
                    throw new UpdateException("操作失败!数据库更新异常!");
                }
            }

            LOGGER.info("批量删除用户<------- {} ---------->成功", Arrays.toString(usernames));
        }
    }

    @Transactional
    public void batchDeleteUnit(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        int result;
        if (PermissionUnit.isAdmin(currentUser)) {
            String[] titles = ParamDivisionUtil.getParams(arg);

            for (String title : titles) {
                if (unitDao.findByTitle(title) == null) {
                    throw new NotFoundException("单位未找到异常, 批量删除失败");
                }
                result = unitDao.deleteByTitle(title);
                if (result == 0) {
                    throw new UpdateException("删除" + title + "单位失败! 数据库更新异常!");
                }
            }

            LOGGER.info("批量删除单位<------- {} ---------->成功", Arrays.toString(titles));
        }
    }

    @Transactional
    public void batchDeleteGetway(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {

    }

    @Transactional
    public void batchDeleteNode(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {

    }
}
