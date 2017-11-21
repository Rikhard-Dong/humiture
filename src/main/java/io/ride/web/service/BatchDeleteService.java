package io.ride.web.service;

import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午6:33
 */
public interface BatchDeleteService {

    void batchDeleteUser(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteUnit(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteGetway(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteNode(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteRepair(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteRent(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

    void batchDeleteUserAuthor(String arg, HttpSession session)
            throws NotFoundException, HasNoPermissionException, UpdateException;

}
