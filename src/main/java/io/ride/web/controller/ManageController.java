package io.ride.web.controller;

import io.ride.web.dto.Result;
import io.ride.web.dto.UserAuthorDto;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.*;
import io.ride.web.service.GetwayNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午11:46
 */
@Controller
@ResponseBody
@RequestMapping("/manage")
public class ManageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private GetwayNodeService getwayNodeService;

    /*
    *******************************************************************
    **********************   节点网关管理   ******************************
    *******************************************************************/

    @DeleteMapping("/node/{mark}")
    public Result deleteNode(@PathVariable("mark") String mark, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            LOGGER.info(user.toString());
        }
        try {
            getwayNodeService.deleteNode(mark, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "删除节点成功");
    }


    /*
    一样的代码, 这里不行下面行, 太诡异了
   @DeleteMapping("/getway/{mark}")
       public Result deleteGetway(@PathVariable("mark") String mark, HttpSession session) {
           UserInfo user = (UserInfo) session.getAttribute("user");
           if(user != null) {
               LOGGER.info(user.toString());
           }
           try {
               getwayNodeService.deleteGetway(mark, session);
           } catch (GetwayNotFoundException e) {
               LOGGER.error(e.getMessage());
               return new Result(false, -1, e.getMessage());
           } catch (HasNoPermissionException e) {
               LOGGER.error(e.getMessage());
               return new Result(false, -1, e.getMessage());
           }
           return new Result(true, 1, "删除节点成功");
       }*/

    @DeleteMapping("/getway/{mark}")
    public Result deleteGetway(@PathVariable("mark") String mark, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            LOGGER.info(user.toString());
        }
        try {
            getwayNodeService.deleteGetway(mark, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "删除网关成功");
    }

    @PostMapping("/getway")
    public Result addGetway(Getway getway, HttpSession session) {
        try {
            getwayNodeService.addGetway(getway, session);
        } catch (Exception e) {
            LOGGER.error("添加网关失败   -->" + e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "添加网关成功");
    }

    @PostMapping("/node")
    public Result addNode(Node node, HttpSession session) {
        node.setType(1);
        try {
            getwayNodeService.addNode(node, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "添加节点成功");
    }

    @PostMapping("/getway/update")
    public Result updateGetway(Getway getway, HttpSession session) {
        LOGGER.info("更新网关信息" + String.valueOf(getway));
        try {
            getwayNodeService.updateGetway(getway, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "更新网关成功");
    }

    @PostMapping("/node/update")
    public Result updateNode(Node node, HttpSession session) {
        try {
            getwayNodeService.updateNode(node, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "更新节点成功");
    }

    @PostMapping("/node/author")
    public Result authorNode(UserAuthorDto userAuthorDto, HttpSession session) {

        return new Result(true, 1, "节点授权成功");
    }
}
