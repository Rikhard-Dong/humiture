package io.ride.web.service;

import io.ride.web.dto.CheckForDto;
import io.ride.web.exception.HasNoPermissionException;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午5:38
 * 检查前端参数是否正确
 */
public interface CheckForService {


    /**
     * 检查节点是否正确
     *
     * @param nodeMark
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    CheckForDto checkForNode(String nodeMark, HttpSession session);

    /**
     * 检查网关是否正确
     *
     * @param getwayMark
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    CheckForDto checkForGetway(String getwayMark, HttpSession session);

    /**
     * 检查单位是否正确
     *
     * @param title
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    CheckForDto checkForUnit(String title, HttpSession session);

    /**
     * 检查用户是否正确
     *
     * @param username
     * @param session
     * @return
     * @throws HasNoPermissionException
     */
    CheckForDto checkForUser(String username, HttpSession session);
}
