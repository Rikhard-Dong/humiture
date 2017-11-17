package io.ride.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import io.ride.web.dao.*;
import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.RentDto;
import io.ride.web.dto.UserAuthorDto;
import io.ride.web.entity.*;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.IsExistsException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.AuthorService;
import io.ride.web.util.MyDateFormat;
import io.ride.web.util.PermissionUnit;
import org.apache.ibatis.annotations.Update;
import org.aspectj.weaver.ast.Not;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午7:56
 */
@Service("authorService")
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private RentDao rentDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private GetwayDao getwayDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private UserAuthorDao userAuthorDao;

    /*
    ***************************
    *******网关授权*************
    **************************/

    public void authorGetway(RentDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isAdmin(user)) {
            Getway getway = getwayDao.findByMark(dto.getGetwayMark(), user.getUserType(), user.getUnitId());
            if (getway == null) {
                throw new NotFoundException("网关不存在");
            }
            Unit unit = unitDao.findByTitle(dto.getTitle());
            if (unit == null) {
                throw new NotFoundException("单位不存在");
            }
            if (unit.getUnitType() == 0) {
                // 特权单位判断授权时间范围内是否已有该单位授权信息
                Rent rent = rentDao.findByUnitIdAndStartTimeAndEndTime(unit.getUnitId(),
                        dto.getStartTime(), dto.getEndTime());
                if (rent != null) {
                    throw new HasNoPermissionException("该时间段内已有租约");
                }
            } else {
                // 普通单位授权时间内只能有一个普通单位授权信息
                Rent rent = rentDao.findByUnitTypeAndStartTimeAndEndTime(dto.getStartTime(), dto.getEndTime());
                if (rent != null) {
                    throw new HasNoPermissionException("该时间段内已有租约");
                }
            }
            Rent rent = new Rent();
            rent.setGetwayId(getway.getGetwayId());
            rent.setUnitId(unit.getUnitId());
            rent.setStartTime(MyDateFormat.str2Date(dto.getStartTime()));
            rent.setEndTime(MyDateFormat.str2Date(dto.getEndTime()));
            rent.setPay(dto.getPay());
            if (rentDao.add(rent) == 0) {
                throw new UpdateException("更新异常");
            }
        } else {
            throw new HasNoPermissionException("没有操作权限");
        }
    }

    public DataTableResult listGetwayAuthor(Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isAdmin(user)) {
            PageHelper.startPage(page, rows);
            List<Rent> rents = rentDao.list();
            PageInfo<Rent> pageInfo = new PageInfo<Rent>(rents);

            List<RentDto> rentDtos = new ArrayList<RentDto>();
            for (Rent rent : pageInfo.getList()) {
                Unit unit = unitDao.findById(rent.getUnitId());
                Getway getway = getwayDao.findById(rent.getGetwayId(), user.getUserType(), user.getUnitId());
                rentDtos.add(new RentDto(rent, getway.getGetwayMark(), unit.getTitle()));
            }
            return new DataTableResult(pageInfo.getTotal(), rentDtos);
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    public void deleteRent(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo userInfo = PermissionUnit.isLogin(session);
        if (PermissionUnit.isAdmin(userInfo)) {
            if (rentDao.delete(id) == 0) {
                throw new UpdateException("数据库更新异常");
            }

        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    /*
    ***************************
    *******节点授权*************
    **************************/

    public void authorNode(UserAuthorDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        if (PermissionUnit.isUnitAdmin(currentUser)) {
            UserInfo user = userInfoDao.findByUsername(dto.getUsername(), currentUser.getUserType(), currentUser.getUnitId());
            if (user == null) {
                throw new NotFoundException("用户不存在");
            }
            Node node = nodeDao.findByMark(dto.getNodeMark(), currentUser.getUserType(), currentUser.getUnitId());
            if (node == null) {
                throw new NotFoundException("节点不存在");
            }
            if (userAuthorDao.isExists(user.getUserId(), node.getNodeId()) != 0) {
                throw new IsExistsException("授权已存在");
            }
            if (userAuthorDao.add(user.getUserId(), node.getNodeId()) == 0) {
                throw new UpdateException("数据库更新异常");
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }
    }

    public DataTableResult listNodeAuthor(Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        if (PermissionUnit.isUnitAdmin(currentUser)) {
            PageHelper.startPage(page, rows);
            List<UserAuthor> userAuthors = userAuthorDao.list(currentUser.getUnitId());
            PageInfo<UserAuthor> pageInfo = new PageInfo<UserAuthor>(userAuthors);
            List<UserAuthorDto> userAuthorDtos = new ArrayList<UserAuthorDto>();
            for (UserAuthor userAuthor : pageInfo.getList()) {
                UserInfo user = userInfoDao.findByUserId(userAuthor.getUserId());
                Node node = nodeDao.findById(userAuthor.getNodeId(), currentUser.getUserType(), currentUser.getUnitId());
                userAuthorDtos.add(new UserAuthorDto(userAuthor.getUserAuthorId(), user.getUsername(), node.getNodeMark()));
            }
            return new DataTableResult(pageInfo.getTotal(), userAuthorDtos);
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }
}
