package io.ride.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.dto.DataTableResult;
import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午7:50
 * <p>
 * 查询控制器
 */
@Controller
@ResponseBody
@RequestMapping(value = "/query")
public class QueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private QueryService queryService;

    @PostMapping("/th/reportTime")
    public DataTableResult queryThByReportTime(@RequestParam(value = "nodeMark") String nodeMark,
                                               @RequestParam(value = "startTime") String startTime,
                                               @RequestParam(value = "endTime") String endTime,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                               HttpSession session) {
        List<TemperHumid> ths;
        PageInfo<TemperHumid> pageInfo;
        try {
            PageHelper.startPage(page, rows);
            ths = queryService.queryThByReportTime(nodeMark, startTime, endTime, session);
            pageInfo = new PageInfo<TemperHumid>(ths);
        } catch (HasNoPermissionException e) {
            LOGGER.error("权限异常-------->", e);
            return null;
        } catch (NotFoundException e) {
            LOGGER.error("未发现 ----->", e);
            return null;
        }

        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
