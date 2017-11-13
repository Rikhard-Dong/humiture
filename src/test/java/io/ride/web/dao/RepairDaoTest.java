package io.ride.web.dao;

import io.ride.web.entity.Repair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午4:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class RepairDaoTest {


    @Autowired
    private RepairDao repairDao;

    @Test
    public void add() throws Exception {
        Repair repair = new Repair();
        repair.setNodeId(1);
        repair.setFaultDesc("故障信息");
        repair.setPerson("ride");
        repair.setPhone("123456789");
        repair.setAddress("11111");
        repair.setStatus(0);
        repairDao.add(repair);
    }

    @Test
    public void list() throws Exception {
        System.out.println("-------------------系统管理员结果------------------------");
        System.out.println(repairDao.list(0, null));
        System.out.println("-------------------单位管理员结果------------------------");
        System.out.println(repairDao.list(1, 1));

    }

    @Test
    public void search() throws Exception {
        System.out.println(repairDao.search("1", 0, null));
        System.out.println(repairDao.search("1", 1, 1));
    }

    @Test
    public void findById() throws Exception {
        System.out.println(repairDao.findById(1, 0, null));
        System.out.println(repairDao.findById(1, 1, 1));
        System.out.println("----------");
        System.out.println(repairDao.findById(5, 0, null));
        System.out.println(repairDao.findById(5, 1, 1));


    }
}