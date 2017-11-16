package io.ride.web.controller;

import io.ride.web.dto.CurrentUserDto;
import io.ride.web.dto.Result;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.UsernameOrPasswordException;
import io.ride.web.service.UnitService;
import io.ride.web.service.UserService;
import org.apache.ibatis.annotations.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:41
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        UserInfo user;
        try {
            user = userService.login(username, password);
        } catch (UsernameOrPasswordException e) {
            LOGGER.error("用户登录失败");
            return new Result(false, -1, e.getMessage());
        }
        session.setAttribute("user", user);
        CurrentUserDto currentUserDto = new CurrentUserDto(user);
        LOGGER.info("user login success --> {}", String.valueOf(session.getAttribute("user")));
        return new Result(true, 1, "用户登录成功").add("user", currentUserDto);
    }

    /**
     * 返回当前用户登录信息的json
     *
     * @param session session
     * @return json
     */
    @GetMapping("/loginUser")
    public CurrentUserDto loginUser(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        LOGGER.info("user login = {}", user);
        if (user == null) {
            return new CurrentUserDto();
        }
        return new CurrentUserDto(user);
    }

    /**
     * 用户登出
     *
     * @param session session
     * @return json
     */
    @RequestMapping("/logout")
    public Result logout(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            return new Result(false, -1, "登出失败! 当前没有登录用户");
        }
        session.removeAttribute("user");
        return new Result(true, 1, "登出成功");
    }

    @PutMapping("/updatePassword/{username}")
    public Result updatePassword(@PathVariable("username") String username,
                                 @RequestParam(value = "oldPassword") String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword,
                                 @RequestParam(value = "newPassword2") String newPassword2,
                                 HttpSession session) {
        // TODO 用户更新自己密码

        return new Result(true, 1, "更新密码成功");
    }
}
