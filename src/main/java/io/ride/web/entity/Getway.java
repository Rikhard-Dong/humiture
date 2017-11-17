package io.ride.web.entity;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午11:38
 */
public class Getway {
    private Integer getwayId;
    private String getwayMark;
    private String spareNode;
    private String nodeNum;
    private Integer status;
    private String nowTemper;
    private String nowHumidity;
    private Integer timeInter;
    private String memo;

    private List<Node> nodes;   // 子节点(包括自身)

    public Integer getGetwayId() {
        return getwayId;
    }

    public void setGetwayId(Integer getwayId) {
        this.getwayId = getwayId;
    }

    public String getGetwayMark() {
        return getwayMark;
    }

    public void setGetwayMark(String getwayMark) {
        this.getwayMark = getwayMark;
    }

    public String getSpareNode() {
        return spareNode;
    }

    public void setSpareNode(String spareNode) {
        this.spareNode = spareNode;
    }

    public String getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(String nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNowTemper() {
        return nowTemper;
    }

    public void setNowTemper(String nowTemper) {
        this.nowTemper = nowTemper;
    }

    public String getNowHumidity() {
        return nowHumidity;
    }

    public void setNowHumidity(String nowHumidity) {
        this.nowHumidity = nowHumidity;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getTimeInter() {
        return timeInter;
    }

    public void setTimeInter(Integer timeInter) {
        this.timeInter = timeInter;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Getway{" +
                "getwayId=" + getwayId +
                ", getwayMark='" + getwayMark + '\'' +
                ", spareNode=" + spareNode +
                ", nodeNum='" + nodeNum + '\'' +
                ", status=" + status +
                ", nowTemper=" + nowTemper +
                ", nowHumidity=" + nowHumidity +
                ", timeInter=" + timeInter +
                ", memo='" + memo + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
