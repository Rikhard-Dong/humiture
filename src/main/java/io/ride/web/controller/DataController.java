package io.ride.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.GetwayDto;
import io.ride.web.dto.RepairDto;
import io.ride.web.entity.*;
import io.ride.web.dto.Result;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    public Object listNodesNotWithoutSubNode(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(value = "rows", required = false, defaultValue = "20") Integer rows,
                                             HttpSession session) {
        LOGGER.info("getways simple, page = {}, rows = {}", page, rows);
        PageHelper.startPage(page, rows);
        List<GetwayDto> getways = null;
        PageInfo<GetwayDto> pageInfo = null;
        try {
            PageHelper.startPage(page, rows);
            getways = getwayNodeService.listGetwaySimple(session);
            pageInfo = new PageInfo<GetwayDto>(getways);

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
    public Object getNodeTree(HttpSession session) {
        List<Map<String, Object>> treeInfo;
        try {
            treeInfo = getwayNodeService.showNodeTree(session);
        } catch (HasNoPermissionException e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

        return treeInfo;
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

    @GetMapping("/units")
    public Result listUnits(HttpSession session) {
        try {

            List<String> units = unitService.listTitles(session);
            return new Result(true, 200, "单位名称列表").add("unints", units);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Result(false, -1, e.getMessage());
        }

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

    @GetMapping("/unitTitles")
    public Object getUnitTiles(HttpSession session) {
        try {
            return unitService.listTitles(session);
        } catch (HasNoPermissionException e) {
            return new Result(false, -1, e.getMessage());
        }
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


    @GetMapping("/usernames")
    public Object listUsernames(HttpSession session) {
        try {
            return userService.listUsernames(session);
        } catch (HasNoPermissionException e) {
            return new Result(false, -1, e.getMessage());

        }
    }

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
        List<RepairDto> repairs;
        PageInfo<RepairDto> pageInfo;

        try {
            PageHelper.startPage(page, rows);
            repairs = repairService.list(session);
            pageInfo = new PageInfo<RepairDto>(repairs);
        } catch (Exception e) {
            LOGGER.error("错误! message = {}", e.getMessage());
            return null;
        }
        return new DataTableResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
