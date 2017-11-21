package io.ride.web.service;

import io.ride.web.entity.THAvg;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午7:28
 */
public interface THAvgService {

    List<THAvg> listDayAvg(String mark, Integer year, Integer month, Integer day, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<THAvg> listDayAvgForMonth(String mark, Integer year, Integer month, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<THAvg> listMonthAvgForYear(String mark, Integer year, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    List<THAvg> listYearAvg(String mark, HttpSession session)
            throws HasNoPermissionException, NotFoundException;

    Map<String, Object> nodeInfo(String mark, HttpSession session)
            throws HasNoPermissionException, NotFoundException;
}
