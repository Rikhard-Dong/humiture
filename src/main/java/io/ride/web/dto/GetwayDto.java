package io.ride.web.dto;

import io.ride.web.dao.GetwayDao;
import io.ride.web.entity.Getway;
import io.ride.web.util.MyDateFormat;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午5:01
 */
public class GetwayDto {
    private Integer getwayId;    // 网关ID
    private String getwayMark;  // 网关号
    private Integer status;     // 网关状态
    private String statusDesc;  // 状态描述
    private String temper;
    private String humidity;
    private String snr;
    private String endTime;     // 结束时间

    public GetwayDto() {
    }

    public GetwayDto(Getway getway) {
        this.getwayId = getway.getGetwayId();
        this.getwayMark = getway.getGetwayMark();
        this.snr = getway.getNodeNum();
        this.status = getway.getStatus();
        this.temper = getway.getNowTemper();
        this.humidity = getway.getNowHumidity();
        if (this.status != null) {
            if (this.status == 0) {
                this.statusDesc = "不在线";
            } else {
                this.statusDesc = "在线";
            }
        }
        this.endTime = "";
    }

    public GetwayDto(Getway getway, Date endTime) {
        this.getwayId = getway.getGetwayId();
        this.getwayMark = getway.getGetwayMark();
        this.status = getway.getStatus();
        this.snr = getway.getNodeNum();
        this.temper = getway.getNowTemper();
        this.humidity = getway.getNowHumidity();
        if (this.status != null) {
            if (this.status == 0) {
                this.statusDesc = "不在线";
            } else {
                this.statusDesc = "在线";
            }
        }
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

    public Integer getGetwayId() {
        return getwayId;
    }

    public void setGetwayId(Integer getwayId) {
        this.getwayId = getwayId;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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
