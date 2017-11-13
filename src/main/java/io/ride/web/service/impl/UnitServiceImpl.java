package io.ride.web.service.impl;

import io.ride.web.dao.UnitDao;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.IsExistsException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.UnitService;
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
 * Date: 17-11-7
 * Time: 下午11:01
 */
@Service("unitService")
public class UnitServiceImpl implements UnitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnitServiceImpl.class);

    @Autowired
    private UnitDao unitDao;

    public List<Unit> list(HttpSession session)
            throws HasNoPermissionException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        List<Unit> units = unitDao.list();
        for (Unit unit : units) {
            unit.setUsers(unitDao.listUnitUser(unit.getUnitId()));
        }
        return units;
    }

    public List<Unit> listSimple(HttpSession session) throws HasNoPermissionException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        return unitDao.list();
    }

    public void addUnit(Unit unit, HttpSession session)
            throws HasNoPermissionException, IsExistsException, UpdateException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        if (unitDao.findByTitle(unit.getTitle()) != null) {
            throw new IsExistsException("添加单位失败, 因为已经存在该单位");
        }
        int result;
        result = unitDao.add(unit);
        if (result == 0) {
            throw new UpdateException("添加该单位失败, 数据库更新异常!");
        }
    }

    public void updateUnit(Unit unit, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        if (unitDao.findByTitle(unit.getTitle()) == null) {
            throw new NotFoundException("无法更新该单位, 因为该单位不存在");
        }
        int result = unitDao.update(unit);
        if (result == 0) {
            throw new UpdateException("更新该单位失败, 数据库更新异常!");
        }
    }

    public void deleteUnit(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        if (!unitDao.isExists(id)) {
            throw new NotFoundException("无法删除该单位, 因为该单位不存在");
        }
        int result = unitDao.deleteById(id);
        if (result == 0) {
            throw new UpdateException("删除该单位失败, 数据库更新异常!");
        }
    }

    public void deleteUnit(String title, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        if (unitDao.findByTitle(title) == null) {
            throw new NotFoundException("无法删除该单位, 因为该单位不存在");
        }
        int result = unitDao.deleteByTitle(title);
        if (result == 0) {
            throw new UpdateException("删除该单位失败, 数据库更新异常!");
        }
    }

    public Unit findUnit(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        Unit unit = unitDao.findById(id);
        if (unit == null) {
            throw new NotFoundException("单位不存在");
        }
        return unit;
    }

    public Unit findUnit(String title, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(userInfo)) {
            throw new HasNoPermissionException("查询失败, 需要系统管理员权限");
        }
        Unit unit = unitDao.findByTitle(title);
        if (unit == null) {
            throw new NotFoundException("单位不存在");
        }
        return unit;
    }
}
