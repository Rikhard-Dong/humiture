package io.ride.web.service;

import io.ride.web.dto.GetwayDto;
import io.ride.web.entity.*;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午7:04
 */
public interface SearchService {
    List<UserInfo> searchUser(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<Unit> searchUnit(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<GetwayDto> searchGetway(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<Node> searchNode(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<Repair> searchRepair(String arg, HttpSession session)
            throws HasNoPermissionException, NotFoundException;
}
