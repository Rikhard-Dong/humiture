package io.ride.web.service.impl;

import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.NodeDao;
import io.ride.web.dao.UnitDao;
import io.ride.web.dao.UserInfoDao;
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
import java.util.List;

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

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private GetwayDao getwayDao;

    public CheckForDto checkForNode(String nodeMark, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        List<Node> nodes;
        if (PermissionUnit.isAdmin(user) || PermissionUnit.isUnitAdmin(user)) {
            nodes = nodeDao.search(nodeMark, user.getUserType(), user.getUnitId());
            if (nodes == null || nodes.size() == 0) {
                return CheckForDto.FALSE_RESULT;
            } else {
                CheckForDto dto = new CheckForDto(true);
                for (Node node : nodes) {
                    dto.add(node.getNodeMark());
                }
                if (nodes.size() == 1) {
                    dto.setOnly(true);
                } else {
                    dto.setOnly(false);
                }
                return dto;
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    public CheckForDto checkForGetway(String getwayMark, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        List<Getway> getways;
        if (PermissionUnit.isAdmin(user) || PermissionUnit.isUnitAdmin(user)) {
            getways = getwayDao.search(getwayMark, user.getUserType(), user.getUnitId());
            if (getways == null || getways.size() == 0) {
                return CheckForDto.FALSE_RESULT;
            } else {
                CheckForDto dto = new CheckForDto(true);
                for (Getway getway : getways) {
                    dto.add(getway.getGetwayMark());
                }
                if (getways.size() == 1) {
                    dto.setOnly(true);
                } else {
                    dto.setOnly(false);
                }
                return dto;
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    public CheckForDto checkForUnit(String title, HttpSession session) {
        UserInfo user = PermissionUnit.isLogin(session);
        List<Unit> units;
        if (PermissionUnit.isAdmin(user)) {
            units = unitDao.search(title);
            if (units == null || units.size() == 0) {
                return CheckForDto.FALSE_RESULT;
            } else {
                CheckForDto dto = new CheckForDto(true);
                for (Unit unit : units) {
                    dto.add(unit.getTitle());
                }
                if (units.size() == 1) {
                    dto.setOnly(true);
                } else {
                    dto.setOnly(false);
                }
                return dto;
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    public CheckForDto checkForUser(String username, HttpSession session) throws HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        List<UserInfo> checkUsers = null;
        if (PermissionUnit.isAdmin(user)) {
            checkUsers = userInfoDao.search(username, user.getUserType(), user.getUnitId());
            if (checkUsers == null || checkUsers.size() == 0) {
                return CheckForDto.FALSE_RESULT;
            } else {
                CheckForDto dto = new CheckForDto(true);
                for (UserInfo userInfo : checkUsers) {
                    dto.add(userInfo.getUsername());
                }
                if (checkUsers.size() == 1) {
                    dto.setOnly(true);
                } else {
                    dto.setOnly(false);
                }
                return dto;
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }
}
