package io.ride.web.service.impl;

import io.ride.web.dao.*;
import io.ride.web.dto.GetwayDto;
import io.ride.web.dto.UserAuthorDto;
import io.ride.web.entity.*;
import io.ride.web.exception.*;
import io.ride.web.service.GetwayNodeService;
import io.ride.web.util.PermissionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午12:45
 */
@Service("getwayNodeService")
public class GetwayNodeServiceImpl implements GetwayNodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetwayNodeService.class);

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private TemperHumidDao temperDao;

    @Autowired
    private GetwayDao getwayDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserAuthorDao userAuthorDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private RentDao rentDao;

    @Transactional
    public void updateGetway(Getway getway, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要系统管理员权限!");
        }
        if (!getwayDao.isExists(getway.getGetwayMark())) {
            LOGGER.info("getway = {}", getway);
            throw new NotFoundException("网关不存在");
        }
        // 更新网关信息同时更新节点表中对应节点的信息
        Node node = new Node();
        node.setNodeMark(getway.getGetwayMark());
        node.setSpareNode(Integer.valueOf(getway.getSpareNode()));
        node.setNodeNum(getway.getNodeNum());
        node.setStatus(getway.getStatus());
        node.setMemo(getway.getMemo());

        int result = nodeDao.updateNodeByMark(node);
        if (result == 0) {
            throw new UpdateException("更新节点失败!");
        }
        int reslut = getwayDao.updateGetwayByMark(getway);
        if (reslut == 0) {
            throw new UpdateException("更新网关失败!");
        }
    }

    @Transactional
    public void updateNode(Node node, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        Integer unitId = user.getUnitId();
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要系统管理员权限!");
        }
        if (!nodeDao.isExists(node.getNodeMark())) {
            throw new NotFoundException("节点不存在");
        }
        int result;
        // 如果当前节点网关, 则连同更新网关信息
        if (node.getType() == 0) {
            LOGGER.info("当前节点为网关节点, 同步更新网关表");
            Getway getway = getwayDao.findByMark(node.getNodeMark(), user.getUserType(), unitId);
            getway.setSpareNode(String.valueOf(node.getSpareNode()));
            getway.setNodeNum(node.getNodeNum());
            getway.setStatus(node.getStatus());
            getway.setMemo(node.getMemo());
            result = getwayDao.updateGetwayByMark(getway);
            if (result == 0) {
                throw new UpdateException("更新网关失败!");
            }
        }
        result = nodeDao.updateNodeByMark(node);
        if (result == 0) {
            throw new UpdateException("更新节点失败!");
        }
    }

    public List<Getway> listGetwayWithSubnode(HttpSession session) {
        UserInfo user = PermissionUtil.isLogin(session);
        Integer unitId = user.getUnit() == null ? null : user.getUnitId();

        List<Getway> getways = getwayDao.list(user.getUserType(), unitId);
        for (Getway getway : getways) {
            getway.setNodes(getwayDao.listSubNode(getway.getGetwayId()));
        }
        return getways;
    }


    /**
     * 查看该单位当前租期内的所有节点
     *
     * @param title
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    public List<Getway> listGetwayWithUnit(String title, HttpSession session) throws HasNoPermissionException {
        // TODO 权限
        List<Getway> getways = unitDao.listRentingGetway(title);
        return getways;
    }

    public List<GetwayDto> listGetwaySimple(HttpSession session) throws HasNoPermissionException {
        UserInfo user = PermissionUtil.isLogin(session);
        List<Getway> getways = getwayDao.list(user.getUserType(), user.getUnitId());
        List<GetwayDto> getwayDtos = new ArrayList<GetwayDto>();
        for (Getway getway : getways) {
            if (user.getUserType() == 0) {
                getwayDtos.add(new GetwayDto(getway));
            } else {
                Rent rent = rentDao.findByGetwayIdAndUnitTileAndCurrentTime(getway.getGetwayId(),
                        user.getUnit().getTitle());
                getwayDtos.add(new GetwayDto(getway, rent.getEndTime()));
            }
        }

        return getwayDtos;
    }


    public List<Node> listNodeWithGetway(String mark, HttpSession session) throws HasNoPermissionException {
        UserInfo user = PermissionUtil.isLogin(session);

        return getwayDao.listSubNodeByNodeMark(mark);
    }

    public Getway findGetwayByMark(String mark, HttpSession session) throws NotFoundException {
        UserInfo user = PermissionUtil.isLogin(session);
        Integer unitId = user.getUnitId();

        LOGGER.info("mark = {}", mark);
        Getway getway = getwayDao.findByMark(mark, user.getUserType(), unitId);
        if (getway == null) {
            LOGGER.error("1111网关不存在");
            throw new NotFoundException("网关不存在");
        }
//        getway.setNodes(getwayDao.listSubNode(getway.getGetwayId()));
        LOGGER.info(getway.toString());
        return getway;
    }

    public Node findNodeByMark(String mark, HttpSession session) throws NotFoundException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(user) || PermissionUtil.isUnitAdmin(user)) {
            Node node = nodeDao.findByMark(mark, user.getUserType(), user.getUnitId());
            if (node == null) {
                throw new NotFoundException("节点不存在");
            }
            return node;
        } else {
            throw new HasNoPermissionException("没有相应的操作权限");
        }

    }

    public Node findNodeById(Integer nodeId, HttpSession session)
            throws NotFoundException, HasNoPermissionException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (PermissionUtil.isAdmin(user) || PermissionUtil.isUnitAdmin(user)) {
            Node node = nodeDao.findById(nodeId, user.getUserType(), user.getUnitId());
            if (node == null) {
                throw new NotFoundException("节点不存在");
            }
            return node;
        } else {
            throw new HasNoPermissionException("权限异常");
        }
    }

    public void deleteGetway(String mark, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo user = (UserInfo) session.getAttribute("user");

        LOGGER.info("login user = {}", user);
        if (user == null) {
            throw new HasNoPermissionException("登录后操作");
        }
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要系统管理员权限!");
        }
        if (!getwayDao.isExists(mark)) {
            throw new NotFoundException("网关不存在");
        }
        int result = getwayDao.deleteByMark(mark);
        if (result == 0) {
            throw new UpdateException("删除网关失败!");
        }
    }

    public void deleteNode(String mark, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            throw new HasNoPermissionException("登录后操作");
        }
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要系统管理员权限!");
        }
        if (!nodeDao.isExists(mark)) {
            throw new NotFoundException("节点不存在");
        }
        Node node = nodeDao.findByMark(mark, user.getUserType(), user.getUnitId());
        int result;
        if (node.getType() == 0) {
            // 删除网关会自动删除对应的节点, 不需要在删除相应的节点了
            result = getwayDao.deleteByMark(mark);
        } else {
            result = nodeDao.deleteByMark(mark);
        }
        if (result == 0) {
            throw new UpdateException("删除节点失败");
        }
    }

    @Transactional
    public void addGetway(Getway getway, HttpSession session)
            throws HasNoPermissionException, IsExistsException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        Integer unitId = user.getUnitId();
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要超级管理员权限!");
        }
        if (getwayDao.isExists(getway.getGetwayMark())) {
            throw new IsExistsException("此网关已存在");
        }
        if (getwayDao.findByMark(getway.getGetwayMark(), user.getUserType(), unitId) != null) {
            throw new IsExistsException("网关已存在");
        }
        int result = getwayDao.addGetway(getway);
        if (result == 0) {
            throw new UpdateException("添加网关失败");
        }
        // 根据网关信息创建节点
        getway = getwayDao.findByMark(getway.getGetwayMark(), user.getUserType(), unitId);

        Node node = new Node();
        node.setNodeMark(getway.getGetwayMark());
        node.setGetwayId(getway.getGetwayId());
        node.setType(0);
        if (getway.getSpareNode() != null) {
            node.setSpareNode(Integer.valueOf(getway.getSpareNode()));
        }
        node.setNodeNum(getway.getNodeNum());
        node.setStatus(getway.getStatus());
        node.setMemo(getway.getMemo());

        if (nodeDao.isExists(node.getNodeMark())) {
            throw new IsExistsException("该nodeMark已被使用!");
        }
        // 添加网关的时候需要把信息同步添加到节点表中
        result = nodeDao.addNode(node);
        if (result == 0) {
            throw new UpdateException("添加节点失败");
        }
    }

    public void addNode(Node node, HttpSession session)
            throws IsExistsException, HasNoPermissionException, UpdateException {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            throw new HasNoPermissionException("登录后操作");
        }
        if (!PermissionUtil.isAdmin(user)) {
            throw new HasNoPermissionException("此操作需要超级管理员权限!");
        }
        if (nodeDao.isExists(node.getNodeMark())) {
            throw new IsExistsException("此节点已经存在!");
        }
        int result = nodeDao.addNode(node);
        if (result == 0) {
            throw new UpdateException("添加节点失败");
        }
    }

    public List<Map<String, Object>> showNodeTree(HttpSession session) throws HasNoPermissionException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        UserInfo user = PermissionUtil.isLogin(session);
        List<Getway> getways = null;
        getways = listGetwayWithSubnode(session);

        if (getways != null) {
            for (Getway getway : getways) {
                String getwayId = getway.getGetwayMark();
                List<String> subNodeIds = new ArrayList<String>();
                for (Node node : getway.getNodes()) {
//                    if (node.getType() == 1) {
                    subNodeIds.add(node.getNodeMark());
//                    }
                }
                Map<String, Object> temp = new HashMap<String, Object>();
                temp.put("parentId", getwayId);
                temp.put("subIds", subNodeIds);
                result.add(temp);
            }
        }

        return result;
    }

    /*
    *******************************************************************
    **********************   节点授权   ********************************
    *******************************************************************/

    @Transactional
    public void nodeAuthorization(Integer userId, Integer nodeId, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (!PermissionUtil.isUnitAdmin(user)) {
            throw new HasNoPermissionException("没有权限操作!");
        }
        UserInfo subUser = userInfoDao.findFromUnitWithUserId(userId, user.getUnitId());
        if (subUser == null) {
            throw new NotFoundException("本单位没有该用户");
        }
        Node node = nodeDao.findById(nodeId, user.getUserType(), user.getUnitId());
        if (node == null) {
            throw new NotFoundException("节点不存在或者没有该节点租约");
        }

        int result;
        if (node.getType() == 1) {
            // 如果该节点是节点的话, 只建立一个授权关系
            result = userAuthorDao.add(userId, nodeId);
            if (result == 0) {
                throw new UpdateException("数据更新异常!");
            }
        } else {
            // 如果该节点是一个网关的话, 则授权该网关和下属节点
            List<Node> nodes = getwayDao.listSubNode(node.getNodeId());
            for (Node subNode : nodes) {
                result = userAuthorDao.add(userId, subNode.getNodeId());
                if (result == 0) {
                    throw new UpdateException("数据更新异常");
                }
            }
        }
    }

    @Transactional
    public void nodeReleaseAuthorization(Integer userId, Integer nodeId, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo user = PermissionUtil.isLogin(session);
        if (!PermissionUtil.isUnitAdmin(user)) {
            throw new HasNoPermissionException("没有权限操作!");
        }
        UserInfo subUser = userInfoDao.findFromUnitWithUserId(userId, user.getUnitId());
        if (subUser == null) {
            throw new NotFoundException("本单位没有该用户");
        }
        Node node = nodeDao.findById(nodeId, user.getUserType(), user.getUnitId());
        if (node == null) {
            throw new NotFoundException("节点不存在或者没有该节点租约");
        }

        int result;
        if (node.getType() == 1) {
            // 如果该节点是节点的话, 只建立一个授权关系
            result = userAuthorDao.delete(userId, nodeId);
            if (result == 0) {
                throw new UpdateException("数据更新异常!");
            }
        } else {
            // 如果该节点是一个网关的话, 则授权该网关和下属节点
            List<Node> nodes = getwayDao.listSubNode(node.getNodeId());
            for (Node subNode : nodes) {
                result = userAuthorDao.delete(userId, subNode.getNodeId());
                if (result == 0) {
                    throw new UpdateException("数据更新异常");
                }
            }
        }
    }

    public void authorNode(UserAuthorDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException {
        UserInfo currentUser = PermissionUtil.isLogin(session);
        if (!PermissionUtil.isUnitAdmin(currentUser)) {
            UserInfo user = userInfoDao.findByUsername(dto.getUsername(),
                    currentUser.getUserType(), currentUser.getUnitId());
            if (user == null) {
                throw new NotFoundException("用户不存在");
            }
            if (user.getUserType() != 3) {
                throw new RuntimeException("只能授权给普通用户");
            }
            Node node = nodeDao.findByMark(dto.getNodeMark(), user.getUserType(), user.getUnitId());
            if (node == null) {
                throw new NotFoundException("节点不存在");
            }
            userAuthorDao.add(user.getUserId(), node.getNodeId());
        } else {
            throw new HasNoPermissionException("没有操作权限");
        }
    }
}
