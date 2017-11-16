package io.ride.web.service;

import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.RentDto;
import io.ride.web.dto.UserAuthorDto;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import org.aspectj.weaver.ast.Not;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午7:56
 */
public interface AuthorService {

    void authorGetway(RentDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    DataTableResult listGetwayAuthor(Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException;

    void authorNode(UserAuthorDto dto, HttpSession session)
            throws HasNoPermissionException, NotFoundException, UpdateException;

    DataTableResult listNodeAuthor(Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException;
}
