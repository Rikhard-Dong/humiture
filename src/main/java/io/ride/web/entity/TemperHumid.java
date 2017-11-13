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
    private Integer temperHumidId;       // 上报温度ID
    private Integer nodeId;         // 上报节点ID
    private Float temper;       // 上报温度
    private Float humidity;        // 上报温度
    private Date reportTime;    // 上报时间
    private short type;         // 上报类型, 0为网关, 1为节点

    private Node parentNode;    // 父节点

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

    public Float getTemper() {
        return temper;
    }

    public void setTemper(Float temper) {
        this.temper = temper;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "TemperHumid{" +
                "temperHumidId=" + temperHumidId +
                ", nodeId=" + nodeId +
                ", temper=" + temper +
                ", humidity=" + humidity +
                ", reportTime=" + MyDateFormat.format(reportTime) +
                ", type=" + type +
                ", parentNode=" + parentNode +
                '}';
    }
}
