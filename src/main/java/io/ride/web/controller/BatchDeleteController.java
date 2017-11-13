package io.ride.web.controller;

import io.ride.web.dto.Result;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.exception.UpdateException;
import io.ride.web.service.BatchDeleteService;
import io.ride.web.util.ParamDivisionUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午6:06
 * <p>
 * <p>
 * 批量删除操作
 */

@Controller
@RequestMapping("/batch")
@ResponseBody
public class BatchDeleteController {
    private static Logger LOGGER = LoggerFactory.getLogger(BatchDeleteController.class);


    @Autowired
    private BatchDeleteService batchDeleteService;

    @DeleteMapping(value = "/user/{arg}")
    public Result deleteUserBatch(@PathVariable("arg") String arg, HttpSession session) {
        LOGGER.info("待删除用户: {}", Arrays.toString(ParamDivisionUtil.getParams(arg)));
        try {
            batchDeleteService.batchDeleteUser(arg, session);
        } catch (Exception e) {
            LOGGER.error("error message = {}! ", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "批量删除用户成功");
    }

    @DeleteMapping(value = "/unit/{arg}")
    public Result deleteUnitBatch(@PathVariable("arg") String arg,
                                  HttpSession session) {
        LOGGER.info("待删除单位: {}", Arrays.toString(ParamDivisionUtil.getParams(arg)));

        try {
            batchDeleteService.batchDeleteUnit(arg, session);
        } catch (Exception e) {
            LOGGER.error("error message = {}! ", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "批量删除单位成功");
    }

    @DeleteMapping(value = "/getway/{arg}")
    public Result deleteGetwayBatch(@PathVariable("arg") String arg,
                                    HttpSession session) {
        LOGGER.info("待删除网关: {}", Arrays.toString(ParamDivisionUtil.getParams(arg)));

        try {
        } catch (Exception e) {
            LOGGER.error("error message = {}! ", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "批量删除网关成功");
    }

    @DeleteMapping(value = "/node/{arg}")
    public Result deleteNodeBatch(@PathVariable("arg") String arg,
                                  HttpSession session) {
        LOGGER.info("待删除节点: {}", Arrays.toString(ParamDivisionUtil.getParams(arg)));
        try {
        } catch (Exception e) {
            LOGGER.error("error message = {}! ", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "批量删除节点成功");
    }

    @DeleteMapping(value = "/repair/{arg}")
    public Result deleteRepairBatch(@PathVariable("arg") String arg, HttpSession session) {
        LOGGER.info("待删除报修信息: {}", Arrays.toString(ParamDivisionUtil.getParams(arg)));

        try {
            batchDeleteService.batchDeleteRepair(arg, session);
        } catch (Exception e) {
            LOGGER.error("error message = {}", e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "批量删除报修信息成功");
    }
}
