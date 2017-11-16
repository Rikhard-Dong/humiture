package io.ride.web.service.impl;

import io.ride.web.dao.*;
import io.ride.web.dto.GetwayDto;
import io.ride.web.entity.*;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.SearchService;
import io.ride.web.util.PermissionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    private RepairDao repairDao;

    @Autowired
    private RentDao rentDao;

    public List<UserInfo> searchUser(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        List<UserInfo> users;
        if (PermissionUnit.isAdmin(currentUser) || PermissionUnit.isUnitAdmin(currentUser)) {
            users = userInfoDao.search(arg, currentUser.getUserType(), currentUser.getUnitId());
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }

        return users;
    }

    public List<Unit> searchUnit(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        List<Unit> units;
        if (PermissionUnit.isAdmin(currentUser)) {
            units = unitDao.search(arg);
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }

        return units;
    }

    public List<GetwayDto> searchGetway(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUnit.isLogin(session);
        List<GetwayDto> getwayDtos = new ArrayList<GetwayDto>();
        if (PermissionUnit.isAdmin(user) || PermissionUnit.isUnitAdmin(user)) {
            List<Getway> getways = getwayDao.search(arg, user.getUserType(), user.getUnitId());
            for (Getway getway : getways) {
                if (PermissionUnit.isAdmin(user)) {
                    getwayDtos.add(new GetwayDto(getway));
                } else if (PermissionUnit.isUnitAdmin(user)) {
                    Rent rent = rentDao.findByGetwayIdAndUnitTileAndCurrentTime(getway.getGetwayId(), user.getUnit().getTitle());
                    getwayDtos.add(new GetwayDto(getway, rent.getEndTime()));
                }
            }
        } else {
            throw new HasNoPermissionException("当前用户没有权限");
        }
        return getwayDtos;
    }

    public List<Node> searchNode(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        return null;
    }

    public List<Repair> searchRepair(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUnit.isLogin(session);
        return repairDao.search(arg, user.getUserType(), user.getUnitId());
    }
}
