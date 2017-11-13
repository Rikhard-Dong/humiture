package io.ride.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.dao.UnitDao;
import io.ride.web.dto.DataTableResult;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
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
            arg = "%" + arg + "%";
            users = searchService.searchUser(arg, session);
            pageInfo = new PageInfo<UserInfo>(users);
        } catch (HasNoPermissionException e) {
            LOGGER.info("搜索用户失败", e);
            return null;
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
        } catch (HasNoPermissionException e) {
            LOGGER.info("搜索用户失败", e);
            return null;
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
