package io.ride.web.controller;

import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.RentDto;
import io.ride.web.dto.Result;
import io.ride.web.dto.UserAuthorDto;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-15
 * Time: 下午7:55
 */
@Controller
@RequestMapping(value = "/author")
@ResponseBody
public class AuthorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @PostMapping("/getway")
    public Result authorGetway(RentDto rentDto, HttpSession session) {
        try {
            authorService.authorGetway(rentDto, session);
        } catch (Exception e) {
            LOGGER.error("author getway error = {}", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "网关授权成功");
    }

    @PostMapping("/rents")
    public DataTableResult rents(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                 HttpSession session) {
        try {
            return authorService.listGetwayAuthor(page, rows, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error("author getway list error = {}", e.getMessage());
            return new DataTableResult();
        }
    }

    @PostMapping("/node")
    public Result authorNode(UserAuthorDto dto, HttpSession session) {
        try {
            authorService.authorNode(dto, session);
        } catch (Exception e) {
            LOGGER.error("author node error = {}", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "节点授权成功");
    }
}
