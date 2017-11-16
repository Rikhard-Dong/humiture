package io.ride.web.dto;

import io.ride.web.entity.UserInfo;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-14
 * Time: 下午8:18
 */
public class CurrentUserDto {
    private boolean isLogin;
    private String username;
    private String name;
    private Integer userType;
    private String userTypeDesc;
    private String unitTile;

    public CurrentUserDto() {
        this.isLogin = false;
    }

    public CurrentUserDto(UserInfo userInfo) {
        this.isLogin = true;
        this.username = userInfo.getUsername();
        this.name = userInfo.getName();
        this.userType = userInfo.getUserType();
        this.userTypeDesc = desc(this.userType);
        if (userInfo.getUnit() != null) {
            this.unitTile = userInfo.getUnit().getTitle();

        }
    }

    private String desc(Integer userType) {
        if (userType == null) {
            return "";
        }

        if (userType == 0) {
            return "系统管理员";
        }
        if (userType == 1) {
            return "特权单位管理员";
        }
        if (userType == 2) {
            return "单位管理员";
        }
        if (userType == 3) {
            return "普通用户";
        }
        return "";
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserTypeDesc() {
        return userTypeDesc;
    }

    public void setUserTypeDesc(String userTypeDesc) {
        this.userTypeDesc = userTypeDesc;
    }

    public String getUnitTile() {
        return unitTile;
    }

    public void setUnitTile(String unitTile) {
        this.unitTile = unitTile;
    }

    @Override
    public String toString() {
        return "CurrentUserDto{" +
                "isLogin=" + isLogin +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", userType=" + userType +
                ", userTypeDesc='" + userTypeDesc + '\'' +
                ", unitTile='" + unitTile + '\'' +
                '}';
    }
}
