package io.ride.web.dao;

import io.ride.web.entity.TemperHumid;
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
 * Date: 17-11-17
 * Time: 下午7:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class TemperHumidDaoTest {

    @Autowired
    private TemperHumidDao temperHumidDao;

    @Test
    public void list() throws Exception {
        System.out.println(temperHumidDao.list());
    }

    @Test
    public void findById() throws Exception {
        System.out.println(temperHumidDao.findById(1));
    }

    @Test
    public void listByNodeMark() throws Exception {
        List<TemperHumid> temperHumids = temperHumidDao.listByNodeMark("SN001-000014-6");
        System.out.println(temperHumids.size());
        System.out.println(temperHumids);
    }

    @Test
    public void listByNodeIdAndTimeSlot() throws Exception {
    }

    @Test
    public void listByNodeMarkWithTime() throws Exception {
    }

}