package io.ride.web.exception;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-8
 * Time: 下午8:41
 */
public class PasswordNotEqualsException extends RuntimeException {
    public PasswordNotEqualsException(String message) {
        super(message);
    }
}
