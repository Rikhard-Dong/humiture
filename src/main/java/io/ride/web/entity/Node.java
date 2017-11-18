package io.ride.web.entity;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-4
 * Time: 下午7:25
 */
public class Node {

    private Integer nodeId;         // 节点ID
    private String nodeMark;        // 节点盒子对应的ID
    private Integer getwayId;       // 所属网关
    private Integer spareNode;      // 是否为备用节点
    private String nodeNum;         // 节点号SNR
    private Integer type;             // 节点类型
    private Integer status;           // 节点状态
    private String nowTemper;        // 节点当前温度
    private String nowHumidity;      // 节点当前湿度
    private String memo;            // 备注

    private Getway getway;          // 所属网关

    public Getway getGetway() {
        return getway;
    }

    public Integer getSpareNode() {
        return spareNode;
    }


    public void setGetway(Getway getway) {
        this.getway = getway;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    public Integer getGetwayId() {
        return getwayId;
    }

    public void setGetwayId(Integer getwayId) {
        this.getwayId = getwayId;
    }


    public void setSpareNode(Integer spareNode) {
        this.spareNode = spareNode;
    }

    public String getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(String nodeNum) {
        this.nodeNum = nodeNum;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Node{" +
                "nodeId=" + nodeId +
                ", nodeMark=" + nodeMark +
                ", getwayId=" + getwayId +
                ", spareNode=" + spareNode +
                ", nodeNum=" + nodeNum +
                ", type=" + type +
                ", status=" + status +
                ", nowTemper=" + nowTemper +
                ", nowHumidity=" + nowHumidity +
                ", memo='" + memo + '\'' +
                '}';
    }
}
