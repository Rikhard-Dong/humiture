package io.ride.web.dto;

import io.ride.web.entity.Getway;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-10
 * Time: 下午3:28
 * <p>
 * 返回jquery-easy-ui 数据表格所需的json格式
 */
public class DataTableResult {
    private long total;
    private Object rows;

    public DataTableResult() {
    }

    public DataTableResult(long total, Object rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "DataTableResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
