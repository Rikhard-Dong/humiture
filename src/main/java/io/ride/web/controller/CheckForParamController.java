package io.ride.web.controller;

import io.ride.web.dto.CheckForDto;
import io.ride.web.service.CheckForService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午5:35
 */
@Controller
@RequestMapping("/checkfor")
@ResponseBody
public class CheckForParamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckForParamController.class);

    @Autowired
    private CheckForService checkForService;


    @GetMapping("/node/{nodeMark}")
    public CheckForDto checkNode(@PathVariable("nodeMark") String nodeMark, HttpSession session) {
        return checkForService.checkForNode(nodeMark, session);
    }

}
