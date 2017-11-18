package io.ride.web.service;

import io.ride.web.dto.DataTableResult;
import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午7:57
 */
public interface QueryService {
    DataTableResult queryThByReportTime(String nodeMark, String startTime, String endTime,
                                        Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException, NotFoundException;
}
