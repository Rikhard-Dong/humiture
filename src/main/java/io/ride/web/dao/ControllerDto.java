package io.ride.web.dao;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-21
 * Time: 下午3:32
 */
public class ControllerDto {
    private String nodenum;
    private String autoflag;
    private String flag2;
    private String snr;
    private String ss1;
    private String ss2;
    private Integer ssvalue1;
    private Integer ssvalue2;


    public String getNodenum() {
        return nodenum;
    }

    public void setNodenum(String nodenum) {
        this.nodenum = nodenum;
    }

    public String getAutoflag() {
        return autoflag;
    }

    public void setAutoflag(String autoflag) {
        this.autoflag = autoflag;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    public String getSs2() {
        return ss2;
    }

    public void setSs2(String ss2) {
        this.ss2 = ss2;
    }

    public Integer getSsvalue1() {
        return ssvalue1;
    }

    public void setSsvalue1(Integer ssvalue1) {
        this.ssvalue1 = ssvalue1;
    }

    public Integer getSsvalue2() {
        return ssvalue2;
    }

    public void setSsvalue2(Integer ssvalue2) {
        this.ssvalue2 = ssvalue2;
    }

    @Override
    public String toString() {
        return "ControllerDto{" +
                "nodenum='" + nodenum + '\'' +
                ", autoflag='" + autoflag + '\'' +
                ", flag2='" + flag2 + '\'' +
                ", snr='" + snr + '\'' +
                ", ss1='" + ss1 + '\'' +
                ", ss2='" + ss2 + '\'' +
                ", ssvalue1=" + ssvalue1 +
                ", ssvalue2=" + ssvalue2 +
                '}';
    }
}
