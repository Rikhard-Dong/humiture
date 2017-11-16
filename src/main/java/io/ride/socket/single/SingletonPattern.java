package io.ride.socket.single;

import io.ride.web.entity.TemperHumid;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午7:38
 * <p>
 * 单利模式
 */
public class SingletonPattern {
    private static final SingletonPattern singletonPattern = new SingletonPattern();

    // 节点的温湿度对象
    private List<TemperHumid> nodeTHList;

    // 实时温湿度
    private Map<String, TemperHumid> nodeNowThMap;

    // 在线socket
    private Map<String, Socket> onlineSocket;

    // 返回信息 废弃
    private String sendResponse = "";

    // 返回信息处理
    private List<String> msgList;

    public synchronized static SingletonPattern getInstance() {
        return singletonPattern;
    }

    public List<TemperHumid> getNodeTHList() {
        return nodeTHList;
    }

    public void setNodeTHList(List<TemperHumid> nodeTHList) {
        this.nodeTHList = nodeTHList;
    }

    public Map<String, TemperHumid> getNodeNowThMap() {
        return nodeNowThMap;
    }

    public void setNodeNowThMap(Map<String, TemperHumid> nodeNowThMap) {
        this.nodeNowThMap = nodeNowThMap;
    }

    public Map<String, Socket> getOnlineSocket() {
        return onlineSocket;
    }

    public void setOnlineSocket(Map<String, Socket> onlineSocket) {
        this.onlineSocket = onlineSocket;
    }

    public String getSendResponse() {
        return sendResponse;
    }

    public void setSendResponse(String sendResponse) {
        this.sendResponse = sendResponse;
    }

    public List<String> getMsgList() {
        if (msgList == null) {
            msgList = new ArrayList<String>();
        }
        return msgList;
    }

    public void setMsgList(List<String> msgList) {
        this.msgList = msgList;
    }

    public void add(String str) {
        if (msgList == null) {
            msgList = new ArrayList<String>();
        }
        msgList.add(str);
    }

    public void remove(String str) {
        if (msgList == null) {
            return;
        }
        msgList.remove(str);
    }
}
