package io.ride.web.dto;

import io.ride.web.entity.TemperHumid;
import io.ride.web.util.MyDateFormat;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午12:03
 */
public class TemperHumidDto {
    private String nodeMark;
    private Integer temper;
    private Integer humidity;
    private String reportTime;

    public TemperHumidDto() {
    }

    public TemperHumidDto(TemperHumid temperHumid) {
        this.nodeMark = temperHumid.getNodeMark();
        if (temperHumid.getTemper() != null) {
            this.temper = Integer.valueOf(temperHumid.getTemper());
        }
        if (temperHumid.getHumidity() != null) {
            this.humidity = Integer.valueOf(temperHumid.getHumidity());
        }
        if (temperHumid.getReportTime() != null) {
            this.reportTime = MyDateFormat.format(temperHumid.getReportTime());
        }
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    public Integer getTemper() {
        return temper;
    }

    public void setTemper(Integer temper) {
        this.temper = temper;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
