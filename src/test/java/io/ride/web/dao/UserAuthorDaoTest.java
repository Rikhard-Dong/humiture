package io.ride.web.dao;

import io.ride.web.entity.UserAuthor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-17
 * Time: 下午7:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class UserAuthorDaoTest {


    @Autowired
    private UserAuthorDao userAuthorDao;
    @Test
    public void findById() throws Exception {
        System.out.println(userAuthorDao.findById(39));
    }
    @Test
    public void add() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void isExists() throws Exception {
    }

    @Test
    public void list() throws Exception {
        System.out.println(userAuthorDao.list(5));
    }

}