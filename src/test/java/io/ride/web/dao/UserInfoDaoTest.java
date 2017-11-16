package io.ride.web.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.entity.UserInfo;
import io.ride.web.util.MD5Encrypt;
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
 * Time: 下午10:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class UserInfoDaoTest {
    @Test
    public void findByUsername() throws Exception {

    }

    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void isAccountExists() throws Exception {
        boolean result = userInfoDao.isAccountExists("admin");
        assertTrue(result);
        result = userInfoDao.isAccountExists("admin1");
        assertFalse(result);
    }

    @Test
    public void accountValidate() throws Exception {
        UserInfo result = userInfoDao.accountValidate("admin", "21232f297a57a5a743894a0e4a801fc3");
        System.out.println(result);
        result = userInfoDao.accountValidate("admin", "admin");
        assertNull(result);
        result = userInfoDao.accountValidate("unit1", "21232f297a57a5a743894a0e4a801fc3");
        System.out.println(result);
    }


    @Test
    public void addUser() {
        UserInfo user = new UserInfo();
        user.setUsername("unit1admin");
        user.setName("unit1admin");
        user.setMemo("unit1admin");
        user.setUnitId(1);
        user.setUserType(1);
        user.setPassword(MD5Encrypt.encrypt("admin"));
        userInfoDao.addUser(user);
    }


    @Test
    public void pageInfo() {
        PageHelper.startPage(1, 10);
        List<UserInfo> users = userInfoDao.list();
        PageInfo<UserInfo> pageInfo = new PageInfo(users);
        System.out.println(pageInfo);
    }
}