package io.ride.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import io.ride.web.dto.DataTableResult;
import io.ride.web.entity.*;
import io.ride.web.dto.Result;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.*;
import io.ride.web.util.MyDateFormat;
import org.apache.ibatis.annotations.Param;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午12:58
 */
@Controller
@ResponseBody
@RequestMapping(value = "/data")
public class DataController {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    // 分页大小
    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private GetwayNodeService getwayNodeService;

    @Autowired
    private TemperHumidityService temperHumidityService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairService repairService;

    private List<TemperHumid> tempers;
    private String startTime;
    private String endTime;

    /*******************************************************************
     **************    温湿度节点数据显示   ********************************
     *******************************************************************/

    @GetMapping("/th/ths")
    public Result ths() {
        // TODO 暂时提供数据接口用
        List<TemperHumid> temperHumids = temperHumidityService.listAllTempersAndHumiditys();
        return new Result(true, 1, "获取所有节点温湿度度信息").add("ths", temperHumids);
    }

    @PostMapping("/th/thss")
    public Map<String, Object> thss(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                    @RequestParam(value = "nodeMark") String nodeMark,
                                    @RequestParam(value = "startTime") String startTime,
                                    @RequestParam(value = "endTime") String endTime,
                                    HttpSession session) {
        // TODO 暂时只提供数据, 权限以后添加
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.startPage(pageNo, rows);
        List<TemperHumid> temperHumids = temperHumidityService.listAllTempersAndHumiditys();
        PageInfo<TemperHumid> pageInfo = new PageInfo<TemperHumid>(temperHumids);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (TemperHumid temperHumid : pageInfo.getList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            Node node = getwayNodeService.findNodeById(temperHumid.getNodeId(), session);
            map.put("id", node.getNodeMark());
            map.put("temper", temperHumid.getTemper());
            map.put("humidity", temperHumid.getHumidity());
            map.put("reportTime", temperHumid.getReportTime());
            list.add(map);
        }
        result.put("total", pageInfo.getTotal());
        result.put("rows", list);

        return result;
    }


    /*******************************************************************
     **************    网关节点数据显示   *********************************
     *******************************************************************/
    /**
     * 显示网关信息包括子节点信息
     *
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @PostMapping(value = "/getways")
    public DataTableResult listNodes(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                     HttpSession session) {
        List<Getway> getways;
        PageInfo<Getway> pageInfo;
        try {
            PageHelper.startPage(page, rows);
            getways = getwayNodeService.listGetwayWithSubnode(session);
            pageInfo = new PageInfo<Getway>(getways);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        if (getways == null) {
            return null;
        } else {
            return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
        }
    }

    /**
     * 显示网关信息不包括节点信息
     *
     * @param page
     * @param rows
     * @param session
     * @return
     */
    @PostMapping(value = "/getways/simple")
    public DataTableResult listNodesNotWithoutSubNode(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                                      HttpSession session) {
        LOGGER.info("getways simple, page = {}, rows = {}", page, rows);
        PageHelper.startPage(page, rows);
        List<Getway> getways = null;
        PageInfo<Getway> pageInfo = null;
        try {
            PageHelper.startPage(page, rows);
            getways = getwayNodeService.listGetwaySimple(session);
            pageInfo = new PageInfo<Getway>(getways);

        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        if (getways == null) {
            return null;
        } else {
            DataTableResult result = new DataTableResult();
            result.setTotal(pageInfo.getTotal());
            result.setRows(pageInfo.getList());
            return result;
        }
    }

    /**
     * 显示单个网关的子节点信息
     *
     * @param session
     * @return
     */
    @PostMapping(value = "/getway/{mark}/subNodes")
    public DataTableResult listAGetwaySubNode(@PathVariable("mark") String mark, HttpSession session) {
        List<Node> nodes = null;
        try {
            nodes = getwayNodeService.listNodeWithGetway(mark, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        List<Node> nodes1 = new ArrayList<Node>();
        if (nodes != null) {
            for (Node node : nodes) {
                if (node.getType() != 0) {
                    nodes1.add(node);
                }
            }

            return new DataTableResult(nodes.size(), nodes);
        } else {
            return null;
        }

    }

    /**
     * 显示该单位租期中的节点
     *
     * @param page
     * @param rows
     * @return
     */
    @PostMapping(value = "/getways/unit/{title}")
    public DataTableResult getGetwayRentingUnit(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                                @PathVariable(value = "title") String title,
                                                HttpSession session) {
        PageHelper.startPage(page, rows);
        List<Getway> getways = null;
        try {
            getways = getwayNodeService.listGetwayWithUnit(title, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        PageInfo<Getway> pageInfo = new PageInfo<Getway>(getways);

        return null;
    }

    /**
     * 根据mark查询网关信息
     *
     * @param mark
     * @param session
     * @return
     */
    @PostMapping(value = "/getway/{mark}")
    public DataTableResult findGetway(@PathVariable("mark") String mark, HttpSession session) {

        Getway getway;
        try {
            getway = getwayNodeService.findGetwayByMark(mark, session);
        } catch (Exception e) {
            LOGGER.error("异常", e);
            return null;
        }
        int total = getway == null ? 0 : 1;
        return new DataTableResult(total, new Getway[]{getway});
    }

    @GetMapping(value = "/node/{mark}")
    public Result findNode(@PathVariable("mark") String mark, HttpSession session) {
        Node node;
        try {
            node = getwayNodeService.findNodeByMark(mark, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "查看网关成功").add("nodey", node);
    }

    @GetMapping(value = "/node/tree")
    public Result getNodeTree(HttpSession session) {
        List<Map<String, Object>> treeInfo;
        try {
            treeInfo = getwayNodeService.showNodeTree(session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "success").add("tree", treeInfo);
    }

    /**
     * 返回指定节点
     *
     * @return
     */
    @PostMapping("/{nodeId}/tempers")
    public Result tempersByNode(@PathVariable(value = "nodeId") int nodeId,
                                @RequestParam(value = "startTime") String startTime,
                                @RequestParam(value = "endTime") String endTime) {
        // TODO 返回指定节点, 时间范围内的温度信息
        LOGGER.info("nodeId={}, startTime={}, endTime={}", nodeId, startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
        PageInfo<UserInfo> pageInfo;

        try {
            tempers = getwayNodeService.getTempersForTimeSlot(nodeId, startTime, endTime);
        } catch (NotFoundException e) {
            LOGGER.error("e={}", e.getMessage());
            return new Result(false, -1, "节点不存在");
        }
        if (tempers == null) {
            return new Result(false, -1, "未查询到相应的信息!");
        }
        return new Result(true, 1, "查询指定节点指定时间范围内温度信息成功!").add("tempers", tempers);
    }


    @GetMapping(value = "/temperExcel/{nodeId}")
    public Result getTemperExcel(HttpServletResponse response, @PathVariable(value = "nodeId") int nodeId) {
        OutputStream outputStream = null;
        try {
            String filename = URLEncoder.encode("节点" + nodeId + "(" + startTime + "至" + endTime + ")"
                    + "温度信息.xls", "utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);

            outputStream = response.getOutputStream();
            HSSFWorkbook workbook = createTempersWorkBook();
            workbook.write(outputStream);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return new Result(false, -1, e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return new Result(false, -1, e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("error={}", e);
                    return new Result(false, -1, e.getMessage());
                }
            }
        }


        return new Result(true, 1, "创建excel成功!");
    }

    /**
     * 根据查询信息生成excel表格
     *
     * @return excel表格
     */
    public HSSFWorkbook createTempersWorkBook() {

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
        cell_01.setCellValue("节点ID");
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

        for (int i = 1; i <= tempers.size(); i++) {
            HSSFRow rowBody = sheet.createRow(i);
            rowBody.setHeight((short) 300);
            TemperHumid temper = tempers.get(i - 1);
            // 节点ID
            HSSFCell c1 = rowBody.createCell(0);
            c1.setCellValue(temper.getNodeId());
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

        return workbook;
    }


    /*******************************************************************
     *******************  单位数据显示   *********************************
     *******************************************************************/


    /**
     * 查询所有单位信息包括所属员工信息
     *
     * @param pageNo
     * @param rows
     * @param session
     * @return
     */
    @PostMapping("/units")
    public DataTableResult listUnits(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
                                     @RequestParam(value = "row", required = false, defaultValue = "20") Integer rows,
                                     HttpSession session) {
        List<Unit> units;
        PageInfo<Unit> pageInfo;
        try {
            PageHelper.startPage(pageNo, rows);
            units = unitService.list(session);
            pageInfo = new PageInfo<Unit>(units);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询所有单位信息不包括员工信息
     *
     * @param pageNo
     * @param rows
     * @param session
     * @return
     */
    @PostMapping("/units/simple")
    public DataTableResult listUnitsSimple(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(value = "row", required = false, defaultValue = "20") Integer rows,
                                           HttpSession session) {
        List<Unit> units;
        PageInfo<Unit> pageInfo;
        try {
            PageHelper.startPage(pageNo, rows);
            units = unitService.listSimple(session);
            pageInfo = new PageInfo<Unit>(units);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());

    }

    @GetMapping("/unit/{title}")
    public Result findByTitle(@PathVariable("title") String title, HttpSession session) {
        Unit unit;

        try {
            LOGGER.info("title = {}", title);
            unit = unitService.findUnit(title, session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }
        return new Result(true, 1, "单位查找成功").add("unit", unit);
    }

    /*******************************************************************
     *******************  用户数据显示   *********************************
     *******************************************************************/

    @PostMapping(value = "/users")
    public DataTableResult listUsers(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                     @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                     HttpSession session) {
        List<UserInfo> users;
        PageInfo<UserInfo> pageInfo;
        try {
            PageHelper.startPage(pageNo, rows);
            users = userService.listUser(session);
            pageInfo = new PageInfo<UserInfo>(users);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping(value = "/user/{username}")
    public Result findUser(@PathVariable("username") String username, HttpSession session) {
        UserInfo user;
        try {
            user = userService.findUser(username, session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return new Result(true, 1, "查找用户成功").add("user", user);
    }


    /*
     *******************************************************************
     *******************  报修数据显示   *********************************
     *******************************************************************/
    @PostMapping("/repairs")
    public DataTableResult listRepairs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                       HttpSession session) {
        List<Repair> repairs;
        PageInfo<Repair> pageInfo;

        try {
            PageHelper.startPage(page, rows);
            repairs = repairService.list(session);
            pageInfo = new PageInfo<Repair>(repairs);
        } catch (HasNoPermissionException e) {
            LOGGER.error("错误! message = {}", e.getMessage());
            return null;
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
