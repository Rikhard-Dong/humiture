package io.ride.web.dao;

import io.ride.web.entity.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午12:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/spring-mybatis.xml")
public class NodeDaoTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(NodeDaoTest.class);

    @Autowired
    private NodeDao nodeDao;

    @Test
    public void listTest() throws Exception {
        LOGGER.info("nodes list dto={}", nodeDao.list());
    }

    @Test
    public void findByIdWithGetwayTest() {
        LOGGER.info("node find by id with getway ={}", nodeDao.findByIdWithGetway(1));
    }

    @Test
    public void findByMarkWithGetway() {
        LOGGER.info("node find by mark with getway ={}", nodeDao.findByMarkWithGetway("1111"));
    }

    @Test
    public void findByIdTest() {
        System.out.println(nodeDao.findByMark("GW001-00007-9", 0, null));
    }

    @Test
    public void findByMarkTest() {
    }

    @Test
    public void addNode() {
        Node node = new Node();
        node.setNodeMark("1113");
        node.setGetwayId(7);
        node.setSpareNode(0);
        node.setNodeNum("125");
        node.setType(1);
        node.setStatus(0);
        node.setMemo("网关1子节点2");
        nodeDao.addNode(node);
    }

    @Test
    public void updateNode() {

    }

    @Test
    public void updateTemperAndHumidity() {
        nodeDao.updateTemperAndHumidity(1, 18.0f, 16.0f);
    }

    @Test
    public void delete() {
        nodeDao.deleteById(2);
        nodeDao.deleteByMark("1113");
    }

    @Test
    public void isExists() {
        assertTrue(nodeDao.isExists("1111"));
        assertFalse(nodeDao.isExists("1115"));

    }

    @Test
    public void isExists1() {
        System.out.println(nodeDao.isExists("123123123123"));
    }

    @Test
    public void findById() {
        System.out.println(nodeDao.findById(159));
    }
}