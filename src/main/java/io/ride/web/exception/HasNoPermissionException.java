package io.ride.web.exception;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午4:39
 */
public class HasNoPermissionException extends RuntimeException {
    public HasNoPermissionException(String message) {
        super(message);
    }
}
