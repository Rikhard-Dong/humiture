package io.ride.web.service;

import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午8:23
 */
public interface TemperHumidityService {
    List<TemperHumid> listAllTempersAndHumiditys();

    List<TemperHumid> listNodeTHWithTime(String nodeMark, String startTime, String endTime, HttpSession session)
            throws HasNoPermissionException, NotFoundException;
}
