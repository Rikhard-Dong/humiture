package io.ride.web.dto;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午5:37
 * <p>
 * dto 判断请求的数据数据是否存在
 */
public class CheckForDto {
    private boolean isExists;

    public CheckForDto() {
    }

    public CheckForDto(boolean isExists) {
        this.isExists = isExists;
    }

    public static CheckForDto TRUE_RESULT = new CheckForDto(true);

    public static CheckForDto FALSE_RESULT = new CheckForDto(false);

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }
}
