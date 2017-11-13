package io.ride.web.service.impl;

import io.ride.web.entity.Getway;
import io.ride.web.service.GetwayNodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午12:48
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-service.xml"})
public class NodeServiceImplTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetwayNodeServiceImpl.class);

    @Autowired
    private GetwayNodeService nodeService;

    @Test
    public void listTest(HttpSession session) throws Exception {
        List<Getway> nodes = nodeService.listGetwayWithSubnode(session);
        LOGGER.info("nodeService list={}", nodes);
    }

}