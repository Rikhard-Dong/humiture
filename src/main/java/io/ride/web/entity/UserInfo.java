package io.ride.web.entity;

/**
 * 用户信息
 *
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午1:10
 */
public class UserInfo {

    private Integer userId;
    private Integer unitId;
    private String title;
    private Integer userType;       // 用户类型:0.系统管理元 1.特权单位管理员 2. 单位管理员 3.用户显示
    private String name;
    private String username;
    private String password;
    private String memo;

    private Unit unit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", unitId=" + unitId +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", memo='" + memo + '\'' +
                ", unit=" + unit +
                '}';
    }
}
