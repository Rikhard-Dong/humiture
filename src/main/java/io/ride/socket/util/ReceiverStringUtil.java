package io.ride.socket.util;

import com.sun.tools.corba.se.idl.StringGen;
import io.ride.socket.single.SingletonPattern;

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

    public static void updateOnlineSocket(SingletonPattern singleton, String dataStr, Socket request) {
        Map<String, Socket> onlineMap;
        if (singleton.getOnlineSocket() == null) {
            onlineMap = new HashMap<String, Socket>();
        } else {
            onlineMap = singleton.getOnlineSocket();
        }
        if (!onlineMap.containsKey(dataStr)) {
            // TODO 更新数据库状态在线状态
        }
        onlineMap.put(dataStr, request);
        onlineMap = updateSocket(onlineMap);
        singleton.setOnlineSocket(onlineMap);
    }

    private static Map<String, Socket> updateSocket(Map<String, Socket> onlineMap) {
        if (onlineMap.size() > 0) {
            for (Map.Entry<String, Socket> entry : onlineMap.entrySet()) {
                String key = entry.getKey();
                Socket value = entry.getValue();
                if (value.isClosed()) {
                    // TODO 更新数据库状态 为不在线
                    onlineMap.remove(key);
                }
            }
        }

        return onlineMap;
    }
}
