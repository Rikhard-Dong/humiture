package io.ride.web.controller;

import io.ride.web.dto.CurrentUserDto;
import io.ride.web.dto.Result;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.UsernameOrPasswordException;
import io.ride.web.service.UnitService;
import io.ride.web.service.UserService;
import io.ride.web.util.RandomValidateCodeUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;

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

    @GetMapping("/code")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("code  is  ------------> {}");
        RandomValidateCodeUtil randCode = new RandomValidateCodeUtil();
        randCode.getRandcode(request, response);
//        HttpSession session = request.getSession();
//        Integer code = (Integer) session.getAttribute(RandomValidateCodeUtil.CODE);
//        LOGGER.info("code  is  ------------> {}", code);
    }


    @GetMapping("/validate")
    public Result validateCode(@RequestParam("code") String validateCode, HttpSession session) {
        String code = (String) session.getAttribute(RandomValidateCodeUtil.CODE);
        LOGGER.info("code  is  ------------> {}", code);
        if (validateCode.equals(code)) {
            return new Result(true, 1, "验证码正确");
        } else {
            return new Result(false, -1, "验证码错误");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        LOGGER.info("username ---> ", username);
        LOGGER.info("password ---> ", password);
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
