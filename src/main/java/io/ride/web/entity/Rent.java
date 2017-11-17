package io.ride.web.entity;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 上午10:10
 */
public class Rent {
    private Integer rentId;
    private Integer getwayId;
    private Integer unitId;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private String pay;
    private Integer status;

    private Unit unit;
    private Getway getway;

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public Integer getGetwayId() {
        return getwayId;
    }

    public void setGetwayId(Integer getwayId) {
        this.getwayId = getwayId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Getway getGetway() {
        return getway;
    }

    public void setGetway(Getway getway) {
        this.getway = getway;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentId=" + rentId +
                ", getwayId=" + getwayId +
                ", unitId=" + unitId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", pay=" + pay +
                ", status=" + status +
                ", unit=" + unit +
                ", getway=" + getway +
                '}';
    }
}
