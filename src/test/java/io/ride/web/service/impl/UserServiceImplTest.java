package io.ride.web.service.impl;

import com.github.pagehelper.PageInfo;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.UsernameOrPasswordException;
import io.ride.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-service.xml"})
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void login() throws Exception {
        UserInfo user = userService.login("admin", "admin");
    }

    @Test(expected = UsernameOrPasswordException.class)
    public void loginFail() {
        UserInfo user = userService.login("admin", "123");
    }

}