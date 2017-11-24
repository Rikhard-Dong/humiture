package io.ride.web.dto;

import io.ride.web.util.MyDateFormat;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-22
 * Time: 下午1:36
 */
public class RealTimeTHInfo implements Comparable {

    private String nodenum;
    private String temper;
    private String humid;
    private String power;
    private Integer type;
    private String reporttime;
    private String snr;

    public RealTimeTHInfo(String nodenum, String temper, String humid, String power, Integer type, String reporttime) {
        this.nodenum = nodenum;
        this.temper = temper;
        this.humid = humid;
        this.power = power;
        this.type = type;
        this.reporttime = reporttime;
    }

    public RealTimeTHInfo(JSONObject object, String snr) throws JSONException {
        this.nodenum = (String) object.get("nodenum");
        this.temper = (String) object.get("temper");
        this.humid = (String) object.get("humid");
        this.power = (String) object.get("power");
        this.type = (Integer) object.get("type");
        Date date = MyDateFormat.str2Date((String) object.get("reporttime"));
        this.reporttime = MyDateFormat.format(date);
        this.snr = snr;
    }

    public String getNodenum() {
        return nodenum;
    }

    public void setNodenum(String nodenum) {
        this.nodenum = nodenum;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    @Override
    public String toString() {
        return "RealTimeTHInfo{" +
                "nodenum='" + nodenum + '\'' +
                ", temper='" + temper + '\'' +
                ", humid='" + humid + '\'' +
                ", power='" + power + '\'' +
                ", type=" + type +
                ", reporttime='" + reporttime + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        RealTimeTHInfo timeTHInfo = (RealTimeTHInfo) o;
        return Integer.valueOf(this.snr) - Integer.valueOf(timeTHInfo.snr);
    }
}
