package io.ride.socket.entity;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-14
 * Time: 下午9:36
 */
public class Message {
    private String msg; // 发送的消息
    private Date last;  // 上次发送时间
    private int times;  // 重发次数

    public Message(String msg) {
        this.msg = msg;
        this.last = new Date();
        this.times = 0;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", last=" + last +
                ", times=" + times +
                '}';
    }
}
