package io.ride.web.entity;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午6:36
 */
public class THAvg {
    private String mark;            // 节点mark
    private String reportTime;        // 可能年, 月, 日
    private Integer temperAvg;  // 平均温度
    private Integer humidityAvg;// 平均湿度

    public THAvg() {
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getTemperAvg() {
        return temperAvg;
    }

    public void setTemperAvg(Integer temperAvg) {
        this.temperAvg = temperAvg;
    }

    public Integer getHumidityAvg() {
        return humidityAvg;
    }

    public void setHumidityAvg(Integer humidityAvg) {
        this.humidityAvg = humidityAvg;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "THAvg{" +
                "mark='" + mark + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", temperAvg=" + temperAvg +
                ", humidityAvg=" + humidityAvg +
                '}';
    }
}
