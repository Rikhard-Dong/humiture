package io.ride.web.util;

import io.ride.web.dao.TemperHumidDao;
import io.ride.web.dao.UserAuthorDao;
import io.ride.web.dao.UserInfoDao;
import io.ride.web.entity.UserAuthor;
import io.ride.web.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午12:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class PermissionUnitTest {
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserAuthorDao userAuthorDao;

    @Autowired
    private TemperHumidDao thDao;

    @Test
    public void test() {
        System.out.println(thDao.listByNodeMarkWithTime("SN001-000007-6", "2016-11-07 18:51:54", "2017-11-09 16:51:54"));
    }

    @Test
    public void isAuthorNode() throws Exception {
        System.out.println(userInfoDao.findByUsername("testUser", 2, 8));
        System.out.println(userAuthorDao.isExists(12, 29));
        System.out.println(PermissionUnit.isAuthorNode("SN001-000014-6", 3, 8, "testUser"));
    }

    @Test
    public void isRepairGetway() throws Exception {
        System.out.println(PermissionUnit.isRepairGetway("GW001-00007-9", 1, 7));


    }

}