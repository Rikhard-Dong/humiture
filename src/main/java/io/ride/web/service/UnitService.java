package io.ride.web.service;

import io.ride.web.entity.Unit;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.IsExistsException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午9:55
 */
public interface UnitService {

    /**
     * 列出所有单位及其所属用户
     *
     * @param session session
     * @return 单位列表
     * @throws HasNoPermissionException 异常
     */
    List<Unit> list(HttpSession session) throws HasNoPermissionException;

    /**
     * 列出所有单位不包含下属用户
     *
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<Unit> listSimple(HttpSession session) throws HasNoPermissionException;

    /**
     * 添加单位
     *
     * @param unit
     * @param session
     * @throws HasNoPermissionException
     * @throws IsExistsException
     * @throws UpdateException
     */
    void addUnit(Unit unit, HttpSession session)
            throws HasNoPermissionException, IsExistsException, UpdateException;

    /**
     * 更新单位信息
     *
     * @param unit
     * @param session
     * @throws HasNoPermissionException
     * @throws UpdateException
     */
    void updateUnit(Unit unit, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    /**
     * 根据单位Id删除节点
     *
     * @param id
     * @param session
     * @throws HasNoPermissionException
     * @throws UpdateException
     */
    void deleteUnit(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    /**
     * 根据单位title删除节点
     *
     * @param title
     * @param session
     * @throws HasNoPermissionException
     * @throws UpdateException
     */
    void deleteUnit(String title, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    /**
     * 根据单位Id查找
     *
     * @param id
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    Unit findUnit(Integer id, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    Unit findUnit(Integer id) throws NotFoundException;

    /**
     * 根据单位title查找
     *
     * @param title
     * @param session
     * @return
     * @throws HasNoPermissionException
     * @throws NotFoundException
     */
    Unit findUnit(String title, HttpSession session)
            throws HasNoPermissionException, NotFoundException;


    /**
     * 列出所有单位名称
     *
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<String> listTitles(HttpSession session) throws HasNoPermissionException;
}
