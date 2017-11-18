package io.ride.web.service.impl;

import io.ride.web.dao.THAvgDao;
import io.ride.web.entity.THAvg;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.THAvgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午7:36
 */
@Service("tHavgService")
public class THAvgServiceImpl implements THAvgService {

    @Autowired
    private THAvgDao thAvgDao;

    public List<THAvg> listDayAvgForMonth(String mark, Integer year, Integer month, HttpSession session)
            throws HasNoPermissionException, NotFoundException {

        return thAvgDao.listDayAvgForMonth(mark, year, month);
    }

    public List<THAvg> listMonthAvgForYear(String mark, Integer year, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        return thAvgDao.listMonthAvgForYear(mark, year);
    }

    public List<THAvg> listYearAvg(String mark, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        return thAvgDao.listYearAvg(mark);
    }
}
