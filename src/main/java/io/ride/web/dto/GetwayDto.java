package io.ride.web.dto;

import io.ride.web.dao.GetwayDao;
import io.ride.web.entity.Getway;
import io.ride.web.util.MyDateFormat;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午5:01
 */
public class GetwayDto {
    private String getwayMark;  // 网关号
    private Integer status;     // 网关状态
    private String statusDesc;  // 状态描述
    private String endTime;     // 结束时间

    public GetwayDto() {
    }

    public GetwayDto(Getway getway) {
        this.getwayMark = getway.getGetwayMark();
        this.status = getway.getStatus();
        this.statusDesc = this.status == 0 ? "不在线" : "在线";
        this.endTime = "";
    }

    public GetwayDto(Getway getway, Date endTime) {
        this.getwayMark = getway.getGetwayMark();
        this.status = getway.getStatus();
        this.statusDesc = this.status == 0 ? "不在线" : "在线";
        this.endTime = MyDateFormat.format(endTime);
    }

    public String getGetwayMark() {
        return getwayMark;
    }

    public void setGetwayMark(String getwayMark) {
        this.getwayMark = getwayMark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "GetwayDto{" +
                "getwayMark='" + getwayMark + '\'' +
                ", status=" + status +
                ", statusDesc='" + statusDesc + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
