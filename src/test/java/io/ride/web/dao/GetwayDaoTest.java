package io.ride.web.dao;

import io.ride.web.entity.Getway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午12:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class GetwayDaoTest {
    @Autowired
    private GetwayDao getwayDao;

    @Test
    public void addGetway() throws Exception {
        Getway getway = new Getway();
        getway.setGetwayMark("12345");
        getway.setSpareNode("0");
        getway.setNodeNum("11111");
        getway.setTimeInter(10);
        getway.setStatus(0);
        getway.setMemo("memo");
        getwayDao.addGetway(getway);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByMark() {
    }

    @Test
    public void updateGetway() {

    }

    @Test
    public void list() {
        System.out.println(getwayDao.list(0, null));
    }

    @Test
    public void listSubNode() {
        System.out.println(getwayDao.listSubNode(1));
    }


    @Test
    public void updateTemperAndHumidity() {
        getwayDao.updateTemperAndHumidity(1, 18.0f, 16.0f);
    }

    @Test
    public void delete() {
        getwayDao.deleteByMark("1111");
    }

    @Test
    public void isExists() {
        assertTrue(getwayDao.isExists("1111"));
        assertFalse(getwayDao.isExists("1112"));
    }

    @Test
    public void test() {

    }

}