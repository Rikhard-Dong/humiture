package io.ride.web.dto;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午4:11
 * <p>
 * 保修信息显示
 */
public class RepairDto {
    private Integer repairId;
    private String nodeMark;
    private String faultDesc;
    private String person;
    private String address;
    private String phone;
    private Integer status;
    private String repairTime;

    public RepairDto() {
    }


    @Override
    public String toString() {
        return "RepairDto{" +
                "repairId=" + repairId +
                ", nodeMark=" + nodeMark +
                ", faultDesc='" + faultDesc + '\'' +
                ", person='" + person + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", repairTime='" + repairTime + '\'' +
                '}';
    }



    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

}
