package io.ride.web.exception;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:30
 * <p>
 * username或者密码错误异常
 */
public class UsernameOrPasswordException extends RuntimeException {
    public UsernameOrPasswordException(String message) {
        super(message);
    }
}
