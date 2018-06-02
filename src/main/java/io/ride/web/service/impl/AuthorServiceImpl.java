package io.ride.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import io.ride.web.util.PermissionUtil;
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
 * Date: 17-11-15
 * Time: 下午7:56
 */
@Service("authorService")
public class AuthorServiceImpl implements AuthorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

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

    public void addRent(RentDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(user)) {
            Getway getway = getwayDao.findByMark(dto.getGetwayMark(), user.getUserType(), user.getUnitId());
            if (getway == null) {
                throw new NotFoundException("网关不存在");
            }
            Unit unit = unitDao.findByTitle(dto.getTitle());
            if (unit == null) {
                throw new NotFoundException("单位不存在");
            }
            if (unit.getUnitType() == 1) {
                // 特权单位判断授权时间范围内是否已有该单位授权信息
                List<Rent> rents = rentDao.findByUnitIdAndStartTimeAndEndTime(unit.getUnitId(),
                        dto.getStartTime(), dto.getEndTime());
                if (rents != null && rents.size() != 0) {
                    throw new HasNoPermissionException("该时间段内已有租约");
                }
            } else {
                // 普通单位授权时间内只能有一个普通单位授权信息
                List<Rent> rent = rentDao.findByUnitTypeAndStartTimeAndEndTime(dto.getStartTime(), dto.getEndTime());
                if (rent != null && rent.size() != 0) {
                    throw new HasNoPermissionException("该时间段内已有其他单位租约");
                }
            }
            Rent rent = new Rent();
            rent.setGetwayId(getway.getGetwayId());
            rent.setUnitId(unit.getUnitId());
            rent.setStartTime(MyDateFormat.str2Date(dto.getStartTime()));
            rent.setEndTime(MyDateFormat.str2Date(dto.getEndTime()));
            rent.setPay(String.valueOf(dto.getPay()));
            if (rentDao.add(rent) == 0) {
                throw new UpdateException("更新异常");
            }
        } else {
            throw new HasNoPermissionException("没有操作权限");
        }
    }

    public void updateRent(RentDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(user)) {
            Getway getway = getwayDao.findByMark(dto.getGetwayMark(), user.getUserType(), user.getUnitId());
            if (getway == null) {
                throw new NotFoundException("网关不存在");
            }
            Unit unit = unitDao.findByTitle(dto.getTitle());
            if (unit == null) {
                throw new NotFoundException("单位不存在");
            }
            Rent rent = rentDao.findByRentId(dto.getRentId());
            if (rent == null) {
                throw new NotFoundException("没有租约信息!");
            }
            if (unit.getUnitType() == 1) {
                // 特权单位判断授权时间范围内是否已有该单位授权信息
                List<Rent> rents = rentDao.findByUnitIdAndStartTimeAndEndTime(unit.getUnitId(),
                        dto.getStartTime(), dto.getEndTime());
                rents.remove(rent);
                if (rents.size() != 0) {
                    throw new HasNoPermissionException("该时间段内已有租约");
                }
            } else {
                // 普通单位授权时间内只能有一个普通单位授权信息
                List<Rent> rents = rentDao.findByUnitTypeAndStartTimeAndEndTime(dto.getStartTime(), dto.getEndTime());
                rents.remove(rent);
                if (rents.size() != 0) {
                    throw new HasNoPermissionException("该时间段内已有其他单位租约");
                }
            }
            rent = new Rent();
            rent.setStartTime(MyDateFormat.str2Date(dto.getStartTime()));
            rent.setEndTime(MyDateFormat.str2Date(dto.getEndTime()));
            rent.setPay(String.valueOf(dto.getPay()));
            if (rentDao.update(rent) == 0) {
                throw new UpdateException("更新异常");
            }
        } else {
            throw new HasNoPermissionException("没有操作权限");
        }
    }

    public DataTableResult listGetwayAuthor(Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(user)) {
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
        UserInfo userInfo = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(userInfo)) {
            if (rentDao.delete(id) == 0) {
                throw new UpdateException("数据库更新异常");
            }

        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }

    public DataTableResult listRentUnitByGetwayMark(String mark, Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo userInfo = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(userInfo)) {
            Getway getway = getwayDao.findByMark(mark, 0, null);
            if (getway != null) {
                PageHelper.startPage(page, rows);
                List<Rent> rents = rentDao.listWithMark(mark);
                PageInfo<Rent> pageInfo = new PageInfo<Rent>(rents);
                List<RentDto> dtos = new ArrayList<RentDto>();
                if (rents != null) {
                    for (Rent rent : pageInfo.getList()) {
                        Unit unit = unitDao.findById(rent.getUnitId());
                        dtos.add(new RentDto(rent, getway.getGetwayMark(), unit.getTitle()));
                    }
                }
                return new DataTableResult(pageInfo.getTotal(), dtos);
            } else {
                throw new NotFoundException("网关未发现");
            }
        } else {
            throw new HasNoPermissionException("权限不足");
        }
    }

    /*
    ***************************
    *******节点授权*************
    **************************/

    public void authorNode(UserAuthorDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        if (PermissionUtil.isUnitAdmin(currentUser)) {
            UserInfo user = userInfoDao.findByUsername(dto.getUsername(), currentUser.getUserType(), currentUser.getUnitId());
            if (user == null) {
                throw new NotFoundException("用户不存在");
            }
            Node node = nodeDao.findByMark(dto.getNodeMark(), currentUser.getUserType(), currentUser.getUnitId());
            if (node == null) {
                throw new NotFoundException("节点不存在");
            }
            if (userAuthorDao.isExists(user.getUserId(), node.getNodeId())) {
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
        UserInfo currentUser = PermissionUtil.isLogin(session);
        if (PermissionUtil.isUnitAdmin(currentUser)) {
            PageHelper.startPage(page, rows);
            List<UserAuthor> userAuthors = userAuthorDao.list(currentUser.getUnitId());
            PageInfo<UserAuthor> pageInfo = new PageInfo<UserAuthor>(userAuthors);
            List<UserAuthorDto> userAuthorDtos = new ArrayList<UserAuthorDto>();
//            LOGGER.info("user authors is {}", pageInfo.getList());
            for (UserAuthor userAuthor : pageInfo.getList()) {
//                LOGGER.info("user authors is {}", userAuthor);
                UserInfo user = userInfoDao.findByUserId(userAuthor.getUserId());
//                LOGGER.info("list node author user {}", user);
                Node node = nodeDao.findByIdAndUserInfo(userAuthor.getNodeId(), currentUser.getUserType(), currentUser.getUnitId());
//                LOGGER.info("list node author node {}", node);
                UserAuthorDto dto = new UserAuthorDto(userAuthor.getUserAuthorId(), node.getNodeMark(), user.getUsername());
//                LOGGER.info("list node author dto {}", dto);
                userAuthorDtos.add(dto);
            }
            return new DataTableResult(pageInfo.getTotal(), userAuthorDtos);
        } else {
            throw new HasNoPermissionException("没有权限");
        }

    }


    public void deleteAuthorNode(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo userInfo = PermissionUtil.isLogin(session);
        if (PermissionUtil.isUnitAdmin(userInfo)) {
            if (userAuthorDao.deleteById(id) == 0) {
                throw new UpdateException("数据库更新异常");
            }

        } else {
            throw new HasNoPermissionException("没有权限");
        }
    }
}
