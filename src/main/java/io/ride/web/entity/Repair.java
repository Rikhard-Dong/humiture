package io.ride.web.entity;

import io.ride.web.dto.RepairDto;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午3:27
 * <p>
 * 节点保修信息实体
 */
public class Repair {

    private Integer repairId;
    private Integer nodeId;
    private String faultDesc;
    private String phone;
    private String person;
    private String address;
    private Date faultTime;
    private Integer status;     // 0. 未处理 1. 处理中 2.处理完成

    public Repair() {
    }


    public Repair(RepairDto repairDto, Integer nodeId) {
        this.repairId = repairDto.getRepairId();
        this.nodeId = nodeId;
        this.faultDesc = repairDto.getFaultDesc();
        this.phone = repairDto.getPhone();
        this.person = repairDto.getPerson();
        this.address = repairDto.getAddress();
    }

    private Node node;

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairId=" + repairId +
                ", nodeId=" + nodeId +
                ", faultDesc='" + faultDesc + '\'' +
                ", phone='" + phone + '\'' +
                ", person='" + person + '\'' +
                ", address='" + address + '\'' +
                ", faultTime=" + faultTime +
                ", status=" + status +
                ", node=" + node +
                '}';
    }
}
