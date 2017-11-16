package io.ride.web.dto;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午7:18
 */
public class UserAuthorDto {
    private Integer userAuthorId;
    private String nodeMark;
    private String username;

    public UserAuthorDto() {
    }

    public UserAuthorDto(String nodeMark, String username) {
        this.nodeMark = nodeMark;
        this.username = username;
    }

    public UserAuthorDto(Integer userAuthorId, String nodeMark, String username) {
        this.userAuthorId = userAuthorId;
        this.nodeMark = nodeMark;
        this.username = username;
    }

    public Integer getUserAuthorId() {
        return userAuthorId;
    }

    public void setUserAuthorId(Integer userAuthorId) {
        this.userAuthorId = userAuthorId;
    }

    public String getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(String nodeMark) {
        this.nodeMark = nodeMark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserAuthorDto{" +
                "userAuthorId=" + userAuthorId +
                ", nodeMark='" + nodeMark + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
