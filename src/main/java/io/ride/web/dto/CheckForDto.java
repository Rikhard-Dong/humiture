package io.ride.web.dto;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isOnly;

    private List<Object> list = new ArrayList<Object>();

    public CheckForDto() {
    }

    public CheckForDto(boolean isExists) {
        this.isExists = isExists;
    }

    public CheckForDto(boolean isExists, boolean isOnly) {
        this.isExists = isExists;
        this.isOnly = isOnly;
    }

    public static CheckForDto TRUE_RESULT = new CheckForDto(true);

    public static CheckForDto FALSE_RESULT = new CheckForDto(false, false);

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }

    public boolean isOnly() {
        return isOnly;
    }

    public void setOnly(boolean only) {
        isOnly = only;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void add(Object obj) {
        list.add(obj);
    }
}
