package io.ride.web.exception;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 下午5:58
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
