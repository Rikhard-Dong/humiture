package io.ride.web.controller;

import java.util.List;

import io.ride.web.dto.DataTableResult;
import io.ride.web.entity.THAvg;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.THAvgService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午7:40
 */
@Controller
@ResponseBody
public class AvgController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvgController.class);

    @Autowired
    private THAvgService thAvgService;


    @GetMapping("/avg/{mark}/{year}/{month}")
    public List<THAvg> listDayAvgForMonth(@PathVariable("mark") String mark,
                                          @PathVariable("year") Integer year,
                                          @PathVariable("month") Integer month,
                                          HttpSession session) {
        try {
            return thAvgService.listDayAvgForMonth(mark, year, month, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for month error = {}", e.getMessage());
            return null;
        }
    }

}
