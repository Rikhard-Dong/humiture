package io.ride.web.dao;

import io.ride.web.entity.THAvg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午6:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class THAvgDaoTest {
    @Autowired
    private THAvgDao thAvgDao;

    @Test
    public void getDayAvgForMonth() throws Exception {
        List<THAvg> thAvgList = thAvgDao.listDayAvgForMonth("SN001-000014-6", 2017, 11);
        System.out.println("日平均--------<" + thAvgList);
        thAvgList = thAvgDao.listMonthAvgForYear("SN001-000014-6", 2017);
        System.out.println("月平均--------<" + thAvgList);
        thAvgList = thAvgDao.listYearAvg("SN001-000014-6");
        System.out.println("年平均--------<" + thAvgList);


    }

    @Test
    public void getMonthAvgForYear() throws Exception {
    }

    @Test
    public void getYearAvg() throws Exception {
    }

}