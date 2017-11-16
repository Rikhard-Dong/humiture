package io.ride.web.dao;

import io.ride.web.entity.Rent;
import io.ride.web.util.MyDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 下午7:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class RentDaoTest {

    @Autowired
    private RentDao rentDao;

    @Test
    public void list() throws Exception {
    }

    @Test
    public void listWithUnit() throws Exception {
    }

    @Test
    public void add() throws Exception {
        Rent rent = new Rent();
        rent.setGetwayId(1);
        rent.setUnitId(2);
        rent.setStartTime(MyDateFormat.str2Date("2017-10-20 10:10:10"));
        rent.setEndTime(MyDateFormat.str2Date("2018-10-10 00:00:00"));
        rent.setPay(9000000f);
        System.out.println(rent);
        rentDao.add(rent);
        System.out.println(rent);
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void updateStatus() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void findByTime() throws Exception {
        Rent rent = rentDao.findByTime("2018-10-10 10:10:10", "2019-10-10 10:10:10");
        System.out.println(rent);
    }


    @Test
    public void findRent() {
        Rent rent = rentDao.findByRentId(1);
        System.out.println(rent);
    }

    @Test
    public void findByUnitIdAndStartTimeAndEndTime() throws Exception {
        System.out.println(rentDao.findByUnitIdAndStartTimeAndEndTime(3,
                "2017-1-1 0:0:0", "2018-1-1 0:0:0"));
        System.out.println(rentDao.findByUnitIdAndStartTimeAndEndTime(3,
                "2018-12-11 0:0:0", "2019-1-1 0:0:0"));
    }

    @Test
    public void findByUnitTypeAndStartTimeAndEndTime() throws Exception {
        System.out.println(rentDao.findByUnitTypeAndStartTimeAndEndTime(
                "2017-1-1 0:0:0", "2018-1-1 0:0:0"));
        System.out.println(rentDao.findByUnitTypeAndStartTimeAndEndTime(
                "2018-12-11 0:0:0", "2019-1-1 0:0:0"));
    }

}