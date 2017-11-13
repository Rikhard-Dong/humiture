package io.ride.web.service;

import io.ride.web.entity.Rent;
import io.ride.web.exception.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 下午12:06
 */
public interface RentService {

    /**
     * 网关与单位授权
     * 只有系统管理员才有权限
     *
     * @param rent    租赁信息
     * @param session session
     * @throws GetwayNotFoundException  网关不存在
     * @throws UnitNotFoundException    单位不存在
     * @throws HasNoPermissionException 权限
     */
    void addRent(Rent rent, HttpSession session)
            throws NotFoundException, HasNoPermissionException;


    /**
     * 更新租赁信息, 只更新时间, 不更新其他
     *
     * @param rent    租赁信息
     * @param session session
     * @throws IsExistsException
     * @throws HasNoPermissionException
     */
    void updateRent(Rent rent, HttpSession session)
            throws NotFoundException, HasNoPermissionException;

    /**
     * 删除租赁信息
     *
     * @param rentId  租赁ID
     * @param session session
     * @throws NotFoundException
     * @throws HasNoPermissionException
     */
    void deleteRent(int rentId, HttpSession session)
            throws NotFoundException, HasNoPermissionException;

    /**
     * 想系统管理员列出所有单位的租赁信息
     *
     * @param session session
     * @return
     * @throws HasNoPermissionException
     */
    List<Map<String, Object>> list( HttpSession session) throws HasNoPermissionException;

}
