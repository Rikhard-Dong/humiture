package io.ride.web.service.impl;

import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.RentDao;
import io.ride.web.dao.UnitDao;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Rent;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.RentService;
import io.ride.web.util.MyDateFormat;
import io.ride.web.util.PermissionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 下午6:35
 */
@Service("rentService")
public class RentServiceImpl implements RentService {
    public static Integer ALL = 0;          // 查询所有状态
    public static Integer IS_END = 1;       // 已结束的租约
    public static Integer IS_USE = 2;       // 租约中
    public static Integer NOT_START = 3;    // 租约未开始

    @Autowired
    private GetwayDao getwayDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private RentDao rentDao;

    public void addRent(Rent rent, HttpSession session)
            throws NotFoundException, HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(user)) {
            throw new HasNoPermissionException("当前用户没有该权限");
        }
        Unit unit = unitDao.findById(rent.getGetwayId());
        Getway getway = getwayDao.findById(rent.getGetwayId(), user.getUserType(), user.getUnitId());
        if (unit == null) {
            throw new NotFoundException("单位未找到");
        }
        if (getway == null) {
            throw new NotFoundException("网关未找到");
        }
        if (unit.getUnitType() == 0 &&
                rentDao.findByTime(MyDateFormat.format(rent.getStartTime()), MyDateFormat.format(rent.getEndTime())) != null) {
            throw new HasNoPermissionException("时间冲突! 同一时间点不能有两个普通单位同时租用");
        }
        int result = rentDao.add(rent);
        if (result == 0) {
            throw new UpdateException("更新租赁信息失败");
        }


    }

    @Transactional
    public void updateRent(Rent rent, HttpSession session)
            throws NotFoundException, HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(user)) {
            throw new HasNoPermissionException("当前用户没有该权限");
        }
        Unit unit = unitDao.findById(rent.getGetwayId());
        if (unit.getUnitType() == 0 &&
                rentDao.findByTime(MyDateFormat.format(rent.getStartTime()), MyDateFormat.format(rent.getEndTime())) != null) {
            throw new HasNoPermissionException("时间冲突! 同一时间点不能有两个普通单位同时租用");
        }
        int result = rentDao.update(rent);
        if (result == 0) {
            throw new UpdateException("更新租赁信息失败");
        }
        result = rentDao.updateStatus(rent.getRentId());
        if (result == 0) {
            throw new UpdateException("更新租赁信息失败");
        }
    }

    public void deleteRent(int rentId, HttpSession session)
            throws NotFoundException, HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(user)) {
            throw new HasNoPermissionException("当前用户没有该权限");
        }
        int result = rentDao.delete(rentId);
        if (result == 0) {
            throw new UpdateException("更新租赁信息失败");
        }
    }

    public List<Map<String, Object>> list(HttpSession session)
            throws HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (!PermissionUnit.isAdmin(user)) {
            throw new HasNoPermissionException("当前用户没有该权限");
        }

        List<Map<String, Object>> result = null;
        if (PermissionUnit.isAdmin(user)) {

        } else if (PermissionUnit.isUnitAdmin(user)) {

        }

        return result;
    }
}
