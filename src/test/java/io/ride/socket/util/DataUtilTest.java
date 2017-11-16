package io.ride.socket.util;

import io.ride.web.dao.UnitDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午8:09
 */
public class DataUtilTest {
    @Test
    public void binaryToHexString() throws Exception {
    }

    @Test
    public void hexString2String() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");
        UnitDao unitDao = (UnitDao) applicationContext.getBean("unitDao");
        assertTrue(unitDao != null);
    }

    @Test
    public void getValueByCode() throws Exception {
        DataUtil.getValueByCode("7B77640805010206040100000100172B22854E7D", "head");

    }

    @Test
    public void test() throws InterruptedException {
        Date now = new Date();
        Thread.sleep(1000);
        Date soon = new Date();

        System.out.println(soon.getTime() - now.getTime());
    }

}