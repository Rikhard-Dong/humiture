package io.ride.web.controller;

import io.ride.web.dao.TemperHumidDao;
import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.Result;
import io.ride.web.entity.TemperHumid;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.service.QueryService;
import io.ride.web.util.MyDateFormat;
import io.ride.web.util.PermissionUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @Autowired
    private TemperHumidDao thDao;

    @PostMapping("/th/reportTime")
    public DataTableResult queryThByReportTime(@RequestParam(value = "nodeMark") String nodeMark,
                                               @RequestParam(value = "startTime") String startTime,
                                               @RequestParam(value = "endTime") String endTime,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                               HttpSession session) {
        try {
            return queryService.queryThByReportTime(nodeMark, startTime, endTime, page, rows, session);
        } catch (HasNoPermissionException e) {
//            e.printStackTrace();
            LOGGER.error("query th by report time error = {}", e.getMessage());
            return new DataTableResult();
        }
    }

    @PostMapping(value = "/excel")
    public Result getTemperExcel(HttpServletResponse response,
                                 @RequestParam(value = "nodeMark") String nodeMark,
                                 @RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime,
                                 HttpSession session) {
        LOGGER.info("请求excel表格");

        UserInfo currentUser;
        try {
            currentUser = PermissionUtil.isLogin(session);
        } catch (HasNoPermissionException e) {
            e.printStackTrace();
            LOGGER.info("未登录!");
            return new Result(false, -1, "用户登录");
        }

        OutputStream outputStream = null;
        try {
            String filename = URLEncoder.encode("节点" + nodeMark + "(" + startTime + "至" + endTime + ")"
                    + "温度信息.xls", "utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);

            outputStream = response.getOutputStream();
            List<TemperHumid> temperHumids = thDao.listByNodeMarkWithTime(nodeMark, startTime, endTime);
            HSSFWorkbook workbook = createTempersWorkBook(temperHumids);
            workbook.write(outputStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return new Result(false, -1, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return new Result(false, -1, e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("error={}", e);
                    return new Result(false, -1, e.getMessage());
                }
            }
        }

        LOGGER.info("请求excel表格成功");
        return new Result(true, 1, "创建excel成功!");
    }

    /**
     * 根据查询信息生成excel表格
     *
     * @return excel表格
     */
    private HSSFWorkbook createTempersWorkBook(List<TemperHumid> tempers) {
        LOGGER.info("excel表格创建开始");

        // 创建表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作薄
        HSSFSheet sheet = workbook.createSheet("sheet 01");
        // 创建样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 创建字体
        HSSFFont font = workbook.createFont();
        // 设置字体
        font.setColor(HSSFFont.COLOR_NORMAL);
        style.setFont(font);

        // 创建表头行
        HSSFRow row = sheet.createRow(0);
        // 创建列
        // 节点ID
        HSSFCell cell_01 = row.createCell(0);
        cell_01.setCellValue("节点Mark");
        cell_01.setCellStyle(style);
        // 节点温度
        HSSFCell cell_02 = row.createCell(1);
        cell_02.setCellValue("节点温度");
        cell_02.setCellStyle(style);
        // 节点湿度
        HSSFCell cell_03 = row.createCell(2);
        cell_03.setCellValue("节点湿度");
        cell_03.setCellStyle(style);
        // 上报时间
        HSSFCell cell_04 = row.createCell(3);
        cell_04.setCellValue("上报时间");
        cell_04.setCellStyle(style);
        LOGGER.info("excel表格表头创建成功");
        if (tempers != null) {
            LOGGER.info("temper size is {}", tempers.size());
            for (int i = 1; i <= tempers.size(); i++) {
                HSSFRow rowBody = sheet.createRow(i);
                rowBody.setHeight((short) 300);
                TemperHumid temper = tempers.get(i - 1);

                if (i == 1) {
                    LOGGER.info("temper is {}", temper);
                }

                // 节点ID
                HSSFCell c1 = rowBody.createCell(0);
                c1.setCellValue(temper.getNodeMark());
                c1.setCellStyle(style);
                // 节点温度
                HSSFCell c2 = rowBody.createCell(1);
                c2.setCellValue(temper.getTemper());
                c2.setCellStyle(style);
                // 节点湿度
                HSSFCell c3 = rowBody.createCell(2);
                c3.setCellValue(temper.getHumidity());
                c3.setCellStyle(style);
                // 上报时间
                HSSFCell c4 = rowBody.createCell(3);
                c4.setCellValue(MyDateFormat.format(temper.getReportTime()));
                c4.setCellStyle(style);
            }
        }

        LOGGER.info("excel表格创建成功");
        return workbook;
    }

}
