package io.ride.web.entity;

import java.util.List;

/**
 * 单位信息
 * <p>
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午1:10
 */
public class Unit {
    private Integer unitId;
    private String title;
    private String address;
    private String person;
    private String phone;
    private String email;
    private Integer unitType;
    private String memo;

    private List<UserInfo> users;   // 单位拥有的用户


    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "unitId=" + unitId +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", person='" + person + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", unitType=" + unitType +
                ", memo='" + memo + '\'' +
                ", users=" + users +
                '}';
    }
}
