package io.ride.web.service.impl;

import io.ride.web.dao.*;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.BatchDeleteService;
import io.ride.web.util.ParamDivisionUtil;
import io.ride.web.util.PermissionUtil;
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


    @Autowired
    private RepairDao repairDao;

    @Autowired
    private RentDao rentDao;

    @Autowired
    private UserAuthorDao userAuthorDao;

    @Transactional
    public void batchDeleteUser(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
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
                if (userInfoDao.findByUsername(username, currentUser.getUserType(), currentUser.getUnitId()) == null) {
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
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(currentUser)) {
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
        UserInfo user = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(user)) {
            String[] marks = ParamDivisionUtil.getParams(arg);
            for (String mark : marks) {
                if (getwayDao.findByMark(mark, user.getUserType(), user.getUnitId()) == null) {
                    throw new NotFoundException("网关未找到");
                }
                result = getwayDao.deleteByMark(mark);
                if (result == 0) {
                    throw new UpdateException("删除" + mark + "网关失败! 数据库更新异常!");
                }
            }
            LOGGER.info("批量删除网关<------- {} ---------->成功", Arrays.toString(marks));

        }
    }

    @Transactional
    public void batchDeleteNode(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(user)) {
            String[] marks = ParamDivisionUtil.getParams(arg);
            for (String mark : marks) {
                if (nodeDao.findByMark(mark, user.getUserType(), user.getUnitId()) == null) {
                    throw new NotFoundException("节点未找到");
                }
                result = nodeDao.deleteByMark(mark);
                if (result == 0) {
                    throw new UpdateException("删除" + mark + "节点失败! 数据库更新异常!");
                }
            }
            LOGGER.info("批量删除节点<------- {} ---------->成功", Arrays.toString(marks));

        }
    }

    @Transactional
    public void batchDeleteRepair(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(currentUser) || PermissionUtil.isUnitAdmin(currentUser)) {
            String[] repairIds = ParamDivisionUtil.getParams(arg);
            for (String repairId : repairIds) {
                Integer id = Integer.valueOf(repairId);
                if (repairDao.findById(id, currentUser.getUserType(), currentUser.getUnitId()) == null) {
                    throw new NotFoundException("保修信息未发现");
                }
                result = repairDao.delete(id);
                if (result == 0) {
                    throw new UpdateException("数据库信息更新异常");
                }
            }
        } else {
            throw new HasNoPermissionException("没有相应的权限");
        }
    }

    @Transactional
    public void batchDeleteRent(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isAdmin(currentUser)) {
            String[] ids = ParamDivisionUtil.getParams(arg);
            for (String id : ids) {
                Integer rentId = Integer.valueOf(id);
                if (rentDao.findByRentId(rentId) == null) {
                    throw new NotFoundException("租用信息未发现");
                }
                result = rentDao.delete(rentId);
                if (result == 0) {
                    throw new UpdateException("数据库信息更新异常");
                }
            }
        } else {
            throw new HasNoPermissionException("没有相应的权限");
        }
    }

    @Transactional
    public void batchDeleteUserAuthor(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        int result;
        if (PermissionUtil.isUnitAdmin(currentUser)) {
            String[] ids = ParamDivisionUtil.getParams(arg);
            for (String id : ids) {
                Integer userAuthorId = Integer.valueOf(id);
                LOGGER.info("batch delete user author user ----> user author id = {}", userAuthorId);
                if (userAuthorDao.findById(userAuthorId) == null) {
                    throw new NotFoundException("租用信息未发现");
                }
                result = userAuthorDao.deleteById(userAuthorId);
                if (result == 0) {
                    throw new UpdateException("数据库信息更新异常");
                }
            }
        } else {
            throw new HasNoPermissionException("没有相应的权限");
        }
    }
}
