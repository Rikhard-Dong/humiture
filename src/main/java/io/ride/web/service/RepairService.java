package io.ride.web.service;

import io.ride.web.dto.RepairDto;
import io.ride.web.entity.Repair;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午4:08
 */
public interface RepairService {

    /**
     * 添加一个节点或者多个节点 一条保修记录
     *
     * @param repairDto
     * @param session
     */
    void add(RepairDto repairDto, HttpSession session)
            throws HasNoPermissionException;

    /**
     * 显示所有节点的保修记录
     *
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    List<RepairDto> list(HttpSession session)
            throws HasNoPermissionException;


    void update(RepairDto repairDto, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    void updateStatus(Integer repairId, Integer status, HttpSession session)
            throws HasNoPermissionException, NotFoundException;
}
