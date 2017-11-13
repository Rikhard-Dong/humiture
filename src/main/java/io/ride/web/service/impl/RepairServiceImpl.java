package io.ride.web.service.impl;

import io.ride.web.dao.NodeDao;
import io.ride.web.dao.RepairDao;
import io.ride.web.dto.RepairDto;
import io.ride.web.entity.Node;
import io.ride.web.entity.Repair;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.RepairService;
import io.ride.web.util.ParamDivisionUtil;
import io.ride.web.util.PermissionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午4:23
 */
@Service("repairService")
public class RepairServiceImpl implements RepairService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepairServiceImpl.class);

    @Autowired
    private RepairDao repairDao;

    @Autowired
    private NodeDao nodeDao;

    public void add(RepairDto repairDto, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isAdmin(user) || PermissionUnit.isUnitAdmin(user)) {
            Node node = nodeDao.findByMark(repairDto.getNodeMark(), user.getUserType(), user.getUnitId());

            if (node == null) {
                throw new NotFoundException("报修的该节点不存在!");
            }
            Repair repair = new Repair(repairDto, node.getNodeId());
            int result = repairDao.add(repair);
            if (result == 0) {
                throw new UpdateException("数据库未更新!");
            }
        } else {
            throw new HasNoPermissionException("没有权限");
        }
    }

    public List<Repair> list(HttpSession session)
            throws HasNoPermissionException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isUnitAdmin(user) || PermissionUnit.isAdmin(user)) {
            return repairDao.list(user.getUserType(), user.getUnitId());
        } else {
            throw new HasNoPermissionException("没有相应的权限");
        }
    }

    public void update(RepairDto repairDto, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isUnitAdmin(user) || PermissionUnit.isAdmin(user)) {
            Repair old = repairDao.findById(repairDto.getRepairId(), user.getUserType(), user.getUnitId());
            if (old == null) {
                throw new NotFoundException("报修信息不存在, 所以无法修改");
            }
            Node node = nodeDao.findByMark(repairDto.getNodeMark());
            Repair repair = new Repair(repairDto, node.getNodeId());
            LOGGER.info("Repair update ---> repair --------> {}", repair);
            int result = repairDao.update(repair);
            if (result == 0) {
                throw new UpdateException("数据库更新数据失败");
            }
        }
    }

    public void updateStatus(Integer repairId, Integer status, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUnit.isLogin(session);
        if (PermissionUnit.isAdmin(user)) {
            if (repairDao.findById(repairId, user.getUserType(), user.getUnitId()) == null) {
                throw new NotFoundException("该报修信息不存在, 无法更新");
            }
            int result = repairDao.updateStatus(repairId, status);
            if (result == 0) {
                throw new UpdateException("数据库更新异常");
            }
        } else {
            throw new HasNoPermissionException("当前用户没有相应的权限");
        }

    }
}