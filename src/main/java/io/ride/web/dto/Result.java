package io.ride.web.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-4
 * Time: 下午9:23
 */
public class Result implements Serializable {
    private boolean success;
    private int code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    public Result(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Result add(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
