package io.ride.web.service.impl;

import io.ride.web.dao.NodeDao;
import io.ride.web.dto.CheckForDto;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.service.CheckForService;
import io.ride.web.util.PermissionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午5:43
 */
@Service("checkForService")
public class CheckForServiceImpl implements CheckForService {

    @Autowired
    private NodeDao nodeDao;

    public CheckForDto checkForNode(String nodeMark, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        Node node = null;
        if (PermissionUnit.isAdmin(user) || PermissionUnit.isUnitAdmin(user)) {
            node = nodeDao.findByMark(nodeMark, user.getUserType(), user.getUnitId());
        }
        if (node != null) {
            return CheckForDto.TRUE_RESULT;
        } else {
            return CheckForDto.FALSE_RESULT;
        }
    }

    public CheckForDto checkForGetway(String getwayMark, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        Getway getway = null;
        if (PermissionUnit.isAdmin(user)) {

        }
        if (getway != null) {
            return CheckForDto.TRUE_RESULT;
        } else {
            return CheckForDto.FALSE_RESULT;
        }
    }

    public CheckForDto checkForUnit(String title, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        Unit unit = null;
        if (PermissionUnit.isAdmin(user)) {

        }
        if (unit != null) {
            return CheckForDto.TRUE_RESULT;
        } else {
            return CheckForDto.FALSE_RESULT;
        }
    }

    public CheckForDto checkForUser(String username, HttpSession session) throws HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        UserInfo checkUser = null;
        if (PermissionUnit.isAdmin(user)) {

        }
        if (checkUser != null) {
            return CheckForDto.TRUE_RESULT;
        } else {
            return CheckForDto.FALSE_RESULT;
        }
    }
}
