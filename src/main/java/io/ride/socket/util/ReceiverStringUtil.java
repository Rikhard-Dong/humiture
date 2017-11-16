package io.ride.socket.util;

import io.ride.socket.single.SingletonPattern;
import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.NodeDao;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午7:18
 * <p>
 * 处理接受字符串工具
 */
public class ReceiverStringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverStringUtil.class);
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-mybatis.xml");


    @Autowired
    private static NodeDao nodeDao = (NodeDao) applicationContext.getBean("nodeDao");

    @Autowired
    private static GetwayDao getwayDao = (GetwayDao) applicationContext.getBean("getwayDao");

    /**
     * 分割客户端传送过来的数据, 有可能存在多个数据包一同发送过来的情况
     *
     * @param hexStr 待分割的数据包
     * @return 多个数据包列表
     */
    public static List<String> splitRtnStr(String hexStr) {
        List<String> list = new ArrayList<String>();
        int start = 0;
        int flag = 0;
        for (int i = 0; i < hexStr.length(); i += 2) {
            flag++;
            if (flag == 1) {
                start = i;
            }
            String reqStr;
            if (hexStr.substring(i, i + 2).equals("7d")) {
                reqStr = hexStr.substring(start, i + 2);
                flag = 0;
                list.add(reqStr);
            }
        }

        return list;
    }

    /**
     * 发送数据给socket
     *
     * @param str     数据内容
     * @param request socket目标
     */
    public static void writeString(String str, Socket request) {
        // 得到CRC16校验
        String crcStr = CRC16Util.getCRC16(DataUtil.hexString2Bytes(DataUtil.string2HexString(str)));
        String response = "{" + str + crcStr + "}";

        try {
            SocketUtil.writeStr2Stream(response, request.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新维护在线socket列表
     *
     * @param singleton
     * @param dataStr
     * @param request
     */
    public static void updateOnlineSocket(SingletonPattern singleton, String dataStr, Socket request) {
        Map<String, Socket> onlineMap;
        if (singleton.getOnlineSocket() == null) {
            onlineMap = new HashMap<String, Socket>();
        } else {
            onlineMap = singleton.getOnlineSocket();
        }
        if (!onlineMap.containsKey(dataStr)) {
            LOGGER.info("update online socket ---> dataStr = {}", dataStr);
            // 更新数据库状态在线状态
            try {
                updateStatus(dataStr, 1);
            } catch (NotFoundException e) {
                LOGGER.error("更新状态失败: {}", e.getMessage());
            }
        }
        onlineMap.put(dataStr, request);
        onlineMap = updateSocket(onlineMap);
        singleton.setOnlineSocket(onlineMap);
    }

    /**
     * 维护当前socket节点列表
     *
     * @param onlineMap
     * @return
     */
    public static Map<String, Socket> updateSocket(Map<String, Socket> onlineMap) {
        if (onlineMap == null) {
            SingletonPattern singleton = SingletonPattern.getInstance();
            singleton.setOnlineSocket(new HashMap<String, Socket>());
            onlineMap = singleton.getOnlineSocket();
        }
        if (onlineMap.size() > 0) {
            for (Map.Entry<String, Socket> entry : onlineMap.entrySet()) {
                String key = entry.getKey();
                Socket value = entry.getValue();
                if (value.isClosed()) {
                    // 更新数据库状态 为不在线
                    try {
                        updateStatus(key, 0);
                    } catch (NotFoundException e) {
                        LOGGER.error("更新状态失败: {}", e.getMessage());
                    }
                    onlineMap.remove(key);
                }
            }
        }

        return onlineMap;
    }

    /**
     * 更新节点状态
     *
     * @param snr
     * @throws NotFoundException
     */
    private static void updateStatus(String snr, Integer status) throws NotFoundException {
        Node node = nodeDao.findByNum(snr);
        if (node == null) {
            throw new NotFoundException("节点不存在");
        }
        node.setStatus(status);
        nodeDao.updateNode(node);
        if (node.getType() == 0) {
            // 设为管理员更新状态
            Getway getway = getwayDao.findById(node.getGetwayId(), 0, null);
            getway.setStatus(status);
            getwayDao.updateGetway(getway);
        }
    }


    public static void updateNowAndAllTH(SingletonPattern singleton, String dataStr) {

    }
}
