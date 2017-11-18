package io.ride.web.dto;

import io.ride.web.entity.Rent;
import io.ride.web.util.MyDateFormat;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午8:00
 */
public class RentDto {
    private Integer rentId;
    private String title;
    private String getwayMark;
    private String startTime;
    private String endTime;
    private Integer pay;
    private String status;


    public RentDto() {
    }

    public RentDto(Rent rent, String getwayMark, String title) {
        this.rentId = rent.getRentId();
        this.title = title;
        this.getwayMark = getwayMark;
        this.startTime = MyDateFormat.format(rent.getStartTime());
        this.endTime = MyDateFormat.format(rent.getEndTime());
        this.pay = rent.getPay() == null ? -1 : Integer.valueOf(rent.getPay());
        this.status = getStatus(rent);
    }

    private String getStatus(Rent rent) {
        String status;
        if (System.currentTimeMillis() < rent.getStartTime().getTime()) {
            status = "未开始";
        } else if (System.currentTimeMillis() > rent.getEndTime().getTime()) {
            status = "已结束";
        } else {
            status = "租约中";
        }
        return status;
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGetwayMark() {
        return getwayMark;
    }

    public void setGetwayMark(String getwayMark) {
        this.getwayMark = getwayMark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentDto{" +
                "rentId=" + rentId +
                ", title='" + title + '\'' +
                ", getwayMark='" + getwayMark + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", pay=" + pay +
                '}';
    }
}
