package io.ride.web.entity;

import io.ride.web.util.MyDateFormat;

import java.util.Date;

/**
 * 节点温度实体
 * <p>
 * Created by IDEA
 * User: ride
 * Date: 17-11-4
 * Time: 下午7:35
 */
public class TemperHumid {
    private Integer temperHumidId;  // 上报温度ID
    private Integer nodeId;         // 上报节点ID
    private String nodeMark;        // 上报节点
    private String temper;          // 上报温度
    private String humidity;        // 上报温度
    private Date reportTime;        // 上报时间
    private Integer type;           // 上报类型, 0为网关, 1为节点

    private Node parentNode;        // 父节点

    public Integer getTemperHumidId() {
        return temperHumidId;
    }

    public void setTemperHumidId(Integer temperHumidId) {
        this.temperHumidId = temperHumidId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    @Override
    public String toString() {
        return "TemperHumid{" +
                "temperHumidId=" + temperHumidId +
                ", nodeId=" + nodeId +
                ", nodeMark='" + nodeMark + '\'' +
                ", temper='" + temper + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reportTime=" + reportTime +
                ", type=" + type +
                ", parentNode=" + parentNode +
                '}';
    }
}
