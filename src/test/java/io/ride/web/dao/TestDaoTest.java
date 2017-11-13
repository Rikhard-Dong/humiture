package io.ride.web.dao;

import io.ride.web.util.MyDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午2:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class TestDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDaoTest.class);
    @Autowired
    private TestDao testDao;

    @Test
    public void test1() throws Exception {
        Date date = new Date();
        LOGGER.info("date={}", date);
        Thread.sleep(2000);
        boolean result = testDao.test(MyDateFormat.format(date));
        LOGGER.info("reslt={}", result);
        assertTrue(result);
    }

}