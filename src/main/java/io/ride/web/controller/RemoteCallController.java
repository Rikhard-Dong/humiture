package io.ride.web.controller;

import io.ride.web.dao.ControllerDto;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.util.JSONHelper;
import io.ride.web.util.PermissionUtil;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-21
 * Time: 下午2:27
 * <p>
 * 远程调用接口
 */
@Controller
@ResponseBody
@RequestMapping("/remote")
public class RemoteCallController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCallController.class);

    /**
     * 节点数据信息获取
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/nodeInfo/{getwayId}")
    public String getNodeInfo(@PathVariable("getwayId") Integer id,
                              HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("getwayId", id);
        try {
            return JSONHelper.getJSON(map, JSONHelper.NODE_INFO_URL);
        } catch (Exception e) {
            LOGGER.info("error --------------------> nodeInfo error = {}", e.getMessage());
            return null;
        }
    }

    /**
     * 单位网关信息
     *
     * @param session
     * @return
     */
    @RequestMapping("/getwayInfo")
    public Object getGetwayInfo(HttpSession session) {
        UserInfo currentUser = null;
        try {
            currentUser = PermissionUtil.isLogin(session);
        } catch (HasNoPermissionException e) {
            LOGGER.info("error --------------------> getwayInfo error = {}", e.getMessage());
            return null;
        }
        Integer unitId = currentUser.getUnitId();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("unitid", unitId);
        try {
            return JSONHelper.getJSON(params, JSONHelper.GETWAY_INFO_URL);
        } catch (Exception e) {
            LOGGER.info("error --------------------> getwayInfo error = {}", e.getMessage());
            return null;
        }
    }

    /**
     * 节点控制
     *
     * @param dto
     * @param session
     */
    @RequestMapping("/nodeSet")
    public void setNode(ControllerDto dto, HttpSession session) {
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println(dto);
        if (dto.getNodenum() != null) {
            params.put("nodenum", dto.getNodenum());
        }
        if (dto.getAutoflag() != null) {
            params.put("autoflag", dto.getAutoflag());
        }
        if (dto.getFlag2() != null) {
            params.put("flag2", dto.getFlag2());
        }
        if (dto.getSnr() != null) {
            params.put("snr", dto.getSnr());
        }
        if (dto.getSs1() != null) {
            params.put("ss1", dto.getSs1());
        }
        if (dto.getSs2() != null) {
            params.put("ss2", dto.getSs2());
        }
        if (dto.getSsvalue1() != null) {
            params.put("ssvalue1", dto.getSsvalue1());
        }
        if (dto.getSsvalue2() != null) {
            params.put("ssvalue2", dto.getSsvalue2());
        }


        try {
            // TODO 启动设置
            // JSONHelper.getJSON(params, JSONHelper.NODE_SET_URL);

        } catch (Exception e) {
            LOGGER.info("error --------------------> nodeSet error = {}", e.getMessage());
        }
    }

    @RequestMapping("/getway/conn")
    public String getwayConn(HttpSession session) {
        UserInfo currentUser = null;
        try {
            currentUser = PermissionUtil.isLogin(session);
        } catch (HasNoPermissionException e) {
            LOGGER.info("error --------------------> node conn error = {}", e.getMessage());
            return null;
        }

        Integer unitId = currentUser.getUserId();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("unitId", unitId);

        try {
            return JSONHelper.getJSON(params, JSONHelper.GETWAY_CONNECTION_URL);
        } catch (Exception e) {
            LOGGER.info("error --------------------> node conn error = {}", e.getMessage());
            return null;
        }
    }

    @RequestMapping("/node/status/{snr}")
    public String getSNRStatus(@RequestParam("snr") String snr, HttpSession session) {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("snr", snr);

            return JSONHelper.getJSON(params, JSONHelper.NODE_CX_URL);
        } catch (Exception e) {
            return null;
        }
    }

}
