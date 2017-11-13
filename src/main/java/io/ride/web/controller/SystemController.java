package io.ride.web.controller;

import io.ride.web.dto.Result;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.*;
import io.ride.web.service.UnitService;
import io.ride.web.service.UserService;
import io.ride.web.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午8:58
 * 系统管理控制器
 */
@Controller
@ResponseBody
@RequestMapping("/system")
public class SystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;

    /*******************************************************************
     **********************   单位管理   *********************************
     *******************************************************************/

    @PostMapping(value = "/unit")
    public Result addUnit(Unit unit, HttpSession session) {
        LOGGER.info("添加单位 = {}", unit);
        try {
            unitService.addUnit(unit, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (IsExistsException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (UpdateException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "添加单位成功");
    }


    @PostMapping(value = "/unit/update")
    public Result updateUnit(Unit unit, HttpSession session) {
        LOGGER.info("更新单位 = {}", unit);

        try {
            unitService.updateUnit(unit, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (UpdateException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "更新单位成功");
    }

    @DeleteMapping(value = "/unit/{title}")
    public Result deleteUnit(@PathVariable("title") String title, HttpSession session) {
        LOGGER.info("title = {}", title);
        try {
            unitService.deleteUnit(title, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (UpdateException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "删除单位成功!");
    }

    /*******************************************************************
     **********************   用户管理   *********************************
     *******************************************************************/

    @PostMapping(value = "/user")
    public Result addUser(UserInfo user,
                          HttpSession session) {
        LOGGER.info("添加用户 = {}", user);
        try {
            userService.addUser(user, session);
        } catch (IsExistsException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (PasswordNotEqualsException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "添加用户成功");
    }

    @PostMapping(value = "/user/update")
    public Result updateUser(UserInfo user, HttpSession session) {
        LOGGER.info("待修改用户信息 = {}", user);
        try {
            userService.updateUser(user, session);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (PasswordNotEqualsException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "更新用户成功");
    }

    @DeleteMapping(value = "/user/{username}")
    public Result deleteUser(@PathVariable("username") String username, HttpSession session) {
        try {
            userService.deleteUser(username, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "删除用户成功");
    }


}
