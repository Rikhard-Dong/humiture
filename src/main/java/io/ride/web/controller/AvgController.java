package io.ride.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.ride.web.entity.THAvg;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.THAvgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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

    @GetMapping("/avg/{mark}/{year}/{month}/{day}")
    public Object listDayAvg(@PathVariable("mark") String mark,
                             @PathVariable("year") Integer year,
                             @PathVariable("month") Integer month,
                             @PathVariable("day") Integer day,
                             HttpSession session) {
        try {
            return thAvgService.listDayAvg(mark, year, month, day, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for day error = {}", e.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msg", e.getMessage());
            return map;
        }
    }


    @GetMapping("/avg/{mark}/{year}/{month}")
    public Object listDayAvgForMonth(@PathVariable("mark") String mark,
                                     @PathVariable("year") Integer year,
                                     @PathVariable("month") Integer month,
                                     HttpSession session) {
        try {
            return thAvgService.listDayAvgForMonth(mark, year, month, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for day error = {}", e.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msg", e.getMessage());
            return map;
        }
    }

    @GetMapping("/avg/{mark}/{year}")
    public Object listMonthAvgForMonth(@PathVariable("mark") String mark,
                                       @PathVariable("year") Integer year,
                                       HttpSession session) {
        try {
            return thAvgService.listMonthAvgForYear(mark, year, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for month error = {}", e.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msg", e.getMessage());
            return map;
        }
    }

    @GetMapping("/avg/{mark}")
    public Object listYearAvg(@PathVariable("mark") String mark,
                              HttpSession session) {
        try {
            return thAvgService.listYearAvg(mark, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for month error = {}", e.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("msg", e.getMessage());
            return map;
        }
    }

    @GetMapping("/avg/{mark}/info")
    public Map<String, Object> nodeInfo(@PathVariable("mark") String mark, HttpSession session) {
        try {
            return thAvgService.nodeInfo(mark, session);
        } catch (Exception e) {
            LOGGER.error("list day avg for month error = {}", e.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", e.getMessage());
            return map;
        }
    }
}
