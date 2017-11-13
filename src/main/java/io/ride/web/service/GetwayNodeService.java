package io.ride.web.service;

import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.*;
import org.apache.ibatis.annotations.Update;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午12:45
 */
public interface GetwayNodeService {
    /**
     * 返回所有网关及其节点信息
     *
     * @return list
     */
    List<Getway> listGetwayWithSubnode(HttpSession session) throws HasNoPermissionException;

    /**
     * 查看网关信息不包括节点信息
     *
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<Getway> listGetwaySimple(HttpSession session) throws HasNoPermissionException;

    /**
     * 查看单个网关的所有节点
     *
     * @param mark
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<Node> listNodeWithGetway(String mark, HttpSession session) throws HasNoPermissionException;

    /**
     * 查询单位租期内的网关信息
     *
     * @param title
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<Getway> listGetwayWithUnit(String title, HttpSession session) throws HasNoPermissionException;

    /**
     * 根据mark返回网关及其节点信息
     *
     * @param mark mark
     * @return getway
     */
    Getway findGetwayByMark(String mark, HttpSession session) throws NotFoundException;

    Node findNodeById(Integer nodeId, HttpSession session) throws NotFoundException, HasNoPermissionException;

    /**
     * 根据mark返回节点信息
     *
     * @param mark mark
     * @return getway
     * @throws NotFoundException 节点不存在
     */
    Node findNodeByMark(String mark, HttpSession session) throws NotFoundException;

    /**
     * 删除网关
     *
     * @param mark 网关mark
     * @throws NotFoundException 异常
     */
    void deleteGetway(String mark, HttpSession httpSession) throws NotFoundException, HasNoPermissionException;

    /**
     * 删除节点
     *
     * @param mark 节点mark
     * @throws NotFoundException 异常
     */
    void deleteNode(String mark, HttpSession httpSession) throws NotFoundException, HasNoPermissionException;

    /**
     * 添加网关信息
     *
     * @param getway      网关信息
     * @param httpSession Session
     * @throws IsExistsException        .
     * @throws HasNoPermissionException .
     * @throws IsExistsException        .
     */
    void addGetway(Getway getway, HttpSession httpSession) throws HasNoPermissionException, IsExistsException;

    /**
     * 添加节点信息
     *
     * @param node    节点信息
     * @param session session
     * @throws IsExistsException        .
     * @throws HasNoPermissionException .
     */
    void addNode(Node node, HttpSession session) throws IsExistsException, HasNoPermissionException;

    /**
     * @param getway
     * @param session
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    void updateGetway(Getway getway, HttpSession session) throws HasNoPermissionException, NotFoundException;

    /**
     * @param node
     * @param session
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    void updateNode(Node node, HttpSession session) throws HasNoPermissionException, NotFoundException;

    /**
     * @param nodeId
     * @param startTime
     * @param endTime
     * @return
     * @throws NotFoundException
     */
    List<TemperHumid> getTempersForTimeSlot(int nodeId, String startTime, String endTime) throws NotFoundException;

    /**
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<Map<String, Object>> showNodeTree(HttpSession session) throws HasNoPermissionException;


    /*
    * ******************************************************************
     **********************   节点授权   ********************************
     *******************************************************************/

    /**
     * 授权操作
     *
     * @param userId
     * @param nodeId
     * @param session
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    void nodeAuthorization(Integer userId, Integer nodeId, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    /**
     * 解除授权操作
     *
     * @param userId
     * @param nodeId
     * @param session
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    void nodeReleaseAuthorization(Integer userId, Integer nodeId, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

}
