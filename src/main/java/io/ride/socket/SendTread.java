package io.ride.socket;

import io.ride.socket.entity.Message;
import io.ride.socket.single.SingletonPattern;
import io.ride.socket.util.DataUtil;
import io.ride.socket.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-14
 * Time: 下午9:47
 */
public class SendTread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendTread.class);

    private boolean result;
    private String snr;
    private String op;
    private Message msg;
    private SingletonPattern singleton;


    public SendTread(String snr, String op, Message msg) {
        this.snr = snr;
        this.msg = msg;
        this.op = op;
        result = false;
        this.singleton = SingletonPattern.getInstance();
    }

    public void run() {
        sendMsg();
    }

    private void sendMsg() {
        LOGGER.info("sendMsg --> 发送请求");
        Map<String, Socket> onlineMap;
        if (singleton.getOnlineSocket() == null) {
            singleton.setOnlineSocket(new HashMap<String, Socket>());
        } else {
            onlineMap = singleton.getOnlineSocket();
            for (Map.Entry<String, Socket> entry : onlineMap.entrySet()) {
                String key = entry.getKey();
                Socket value = entry.getValue();
                if (snr.equals(key)) {
                    try {
                        LOGGER.info("Runnable sendMsg 发送数据 ----> {}", msg.getMsg());
                        while (msg.getTimes() < 3) {
                            msg.setTimes(msg.getTimes() + 1);
                            SocketUtil.writeStr2Stream(msg.getMsg(), value.getOutputStream());
                            msg.setLast(new Date());
                            do {
                                for (String str : singleton.getMsgList()) {
                                    result = isResponse(str);
                                    if (result) {
                                        return;
                                    }
                                }
                                Thread.sleep(200);
                            } while (System.currentTimeMillis() - msg.getLast().getTime() <= 3000);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Runnable sendMsg error = {}", e.getMessage());
                    }
                    break;
                }
            }
        }

    }

    private boolean isResponse(String str) {
        String responseSnr = DataUtil.getValueByCode(str, "snr");

        /*
         * '7374' --> st 设置
         * '6378' --> cx 查询
         * '6371' --> cq 重启
         * '7362' --> sb 上报
         **/
        if (str.contains("7374") && op.equals("st")) {
            if (responseSnr.equals(snr)) {
                singleton.remove(str);
                return true;
            } else {
                return false;
            }
        }
        if (str.contains("6371") && op.equals("cq")) {
            // 判断是否是对应的重启回应
            if (responseSnr.equals(snr)) {
                singleton.remove(str);
                return true;
            } else {
                return false;
            }
        }
        if (str.contains("6378") && op.equals("cx")) {
            if (responseSnr.equals(snr)) {
                singleton.remove(str);
                return true;
            } else {
                return false;
            }

        }
        if (str.contains("7362") && op.equals("sb")) {
            if (responseSnr.equals(snr)) {
                singleton.remove(str);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getResult() {
        return result;
    }
}
