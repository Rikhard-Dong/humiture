package io.ride.web.dao;

import io.ride.web.entity.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午9:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class UnitDaoTest {

    @Autowired
    private UnitDao unitDao;
    @Test
    public void listByRent() throws Exception {
        System.out.println(unitDao.listByRent("GW001-00008-2"));
    }

    @Test
    public void list() throws Exception {
        System.out.println(unitDao.list());
    }

    @Test
    public void listUnitUser() throws Exception {
        System.out.println(unitDao.listUnitUser(1));
    }

    @Test
    public void add() throws Exception {
        Unit unit = new Unit();
        unit.setTitle("单位3");
        unit.setAddress("地球");
        unit.setPerson("我");
        unit.setPhone("110");
        unit.setEmail("123@qq.com");
        unit.setUnitType(0);
        unit.setMemo("11");
        unitDao.add(unit);
    }

    @Test
    public void update() throws Exception {
        Unit unit = new Unit();
        unit.setUnitId(8);
        unit.setMemo("这是单位3");
        unitDao.update(unit);
    }

    @Test
    public void delete() throws Exception {
        System.out.println(unitDao.deleteById(8));
    }

    @Test
    public void findById() throws Exception {
        System.out.println(unitDao.findById(1));
    }

}