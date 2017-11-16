package io.ride.web.entity;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午7:17
 */
public class UserAuthor {
    private Integer userAuthorId;
    private Integer nodeId;
    private Integer userId;

    public Integer getUserAuthorId() {
        return userAuthorId;
    }

    public void setUserAuthorId(Integer userAuthorId) {
        this.userAuthorId = userAuthorId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserAuthor{" +
                "userAuthorId=" + userAuthorId +
                ", nodeId=" + nodeId +
                ", userId=" + userId +
                '}';
    }
}
