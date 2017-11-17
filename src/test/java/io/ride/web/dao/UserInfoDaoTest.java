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
        boolean result = userInfoDao.isAccountExists("中铁十二局七公司");
        System.out.println(result);
        assertTrue(result);
        result = userInfoDao.isAccountExists("admin111");
        assertFalse(result);
        System.out.println(result);
    }

    @Test
    public void accountValidate() throws Exception {
        System.out.println(userInfoDao.accountValidate("admin", "55555"));
    }

    @Test
    public void list() throws Exception {
        System.out.println(userInfoDao.list());
    }

    @Test
    public void addUser() {
        UserInfo user = new UserInfo();
        user.setUsername("unit1admin");
        user.setName("unit1admin");
        user.setMemo("unit1admin");
        user.setUnitId(1);
        user.setUserType(1);
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