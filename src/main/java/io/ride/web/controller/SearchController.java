package io.ride.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.dao.UnitDao;
import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.GetwayDto;
import io.ride.web.entity.Repair;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.SearchService;
import io.ride.web.service.UnitService;
import io.ride.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午4:15
 */
@Controller
@RequestMapping("/search")
@ResponseBody
public class SearchController {
    private static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @PostMapping(value = "/user/{arg}")
    public DataTableResult searchUser(@PathVariable("arg") String arg,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                      HttpSession session) {
        List<UserInfo> users;
        PageInfo<UserInfo> pageInfo;
        try {
            PageHelper.startPage(page, rows);
            users = searchService.searchUser(arg, session);
            pageInfo = new PageInfo<UserInfo>(users);
        } catch (Exception e) {
            LOGGER.info("error message = {}", e.getMessage());
            return new DataTableResult();
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping(value = "/unit/{arg}")
    public DataTableResult searchUnit(@PathVariable("arg") String arg,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                      HttpSession session) {
        List<Unit> units;
        PageInfo<Unit> pageInfo;
        try {
            PageHelper.startPage(page, rows);
            units = searchService.searchUnit(arg, session);
            pageInfo = new PageInfo<Unit>(units);
        } catch (Exception e) {
            LOGGER.info("error message = {}", e.getMessage());
            return new DataTableResult();
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping(value = "/repair/{arg}")
    public DataTableResult searchRepair(@PathVariable("arg") String arg,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                        HttpSession session) {
        List<Repair> repairs;
        PageInfo<Repair> pageInfo;

        try {
            PageHelper.startPage(page, rows);
            repairs = searchService.searchRepair(arg, session);
            pageInfo = new PageInfo<Repair>(repairs);
        } catch (Exception e) {
            LOGGER.info("error message = {}", e.getMessage());
            return new DataTableResult();
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping(value = "/getway/{arg}")
    public DataTableResult searchGetway(@PathVariable("arg") String arg,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                        HttpSession session) {
        List<GetwayDto> getwayDtos;
        PageInfo<GetwayDto> pageInfo;

        try {
            PageHelper.startPage(page, rows);
            getwayDtos = searchService.searchGetway(arg, session);
            pageInfo = new PageInfo<GetwayDto>(getwayDtos);
        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.info("search getways error message = {}", e.getMessage());
            return new DataTableResult();
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
