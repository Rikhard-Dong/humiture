package io.ride.web.controller;

import io.ride.web.dao.NodeDao;
import io.ride.web.dto.ControllerDto;
import io.ride.web.dto.RealTimeTHInfo;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.util.JSONHelper;
import io.ride.web.util.PermissionUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

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


    @Autowired
    private NodeDao nodeDao;

    /**
     * 节点数据信息获取
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/nodeInfo/{getwayId}")
    public Object getNodeInfo(@PathVariable("getwayId") Integer id,
                              HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("getwayId", id);
        try {
            String result = JSONHelper.getJSON(map, JSONHelper.NODE_INFO_URL);
            JSONArray array = new JSONArray(result);
            JSONObject[] objects = new JSONObject[array.length()];
            List<RealTimeTHInfo> infos = new ArrayList<RealTimeTHInfo>();
            for (int i = 0; i < array.length(); i++) {
                objects[i] = array.optJSONObject(i);
//                LOGGER.info("\n-------------node info ----------------\n\t\t\t\t{}\n--------------------------------", objects[i].get("nodenum"));
                String snr = nodeDao.findByMark((String) objects[i].get("nodenum"), 0, null).getNodeNum();
                if (snr.length() <= 2) {
                    infos.add(new RealTimeTHInfo(objects[i], snr));
                }
            }
            // 填充空白
            int snr = 1;
            int index = 0;
            Collections.sort(infos);
            while (snr != 9) {
                if (index >= infos.size() || snr != Integer.valueOf(infos.get(index).getSnr())) {
                    RealTimeTHInfo realTimeTHInfo = new RealTimeTHInfo("-1", "-1", "-1", "-1", -1, "-1");
                    realTimeTHInfo.setSnr("0" + snr);
                    infos.add(realTimeTHInfo);
                    index++;
                    snr++;
                } else if (snr == Integer.valueOf(infos.get(index).getSnr())) {
                    snr++;
                    index++;
                }
            }
            return infos;
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
        UserInfo currentUser;
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


        if (dto.getAutoflag() != null) {
            params.put("autoflag", dto.getAutoflag());
            if (dto.getAutoflag().equals("F")) {
                if (dto.getNodenum() != null) {
                    params.put("nodenum", dto.getNodenum());
                }
                if (dto.getSnr() != null) {
                    params.put("snr", dto.getSnr());
                }
                if (dto.getFlag() != null) {
                    params.put("flag", dto.getFlag());
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
            } else if (dto.getAutoflag().equals("0")) {
                if (dto.getNodenum() != null) {
                    params.put("nodenum", dto.getNodenum());
                }
                if (dto.getFlag2() != null) {
                    params.put("flag2", dto.getFlag2());
                }
                if (dto.getSnr() != null) {
                    params.put("snr", dto.getSnr());
                }
            }
        }


        try {

            StringBuilder paramsStr = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramsStr.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            LOGGER.info("请求参数 ------ >" + paramsStr);
            // TODO 启动设置
            // JSONHelper.getJSON(params, JSONHelper.NODE_SET_URL);

        } catch (Exception e) {
            LOGGER.info("error --------------------> nodeSet error = {}", e.getMessage());
        }
    }

    @RequestMapping("/getway/conn")
    public String getwayConn(HttpSession session) {
        UserInfo currentUser;
        try {
            currentUser = PermissionUtil.isLogin(session);
        } catch (HasNoPermissionException e) {
            LOGGER.info("error --------------------> node conn error = {}", e.getMessage());
            return null;
        }

        Integer unitId = currentUser.getUnitId();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("unitid", unitId);

        try {
            return JSONHelper.getJSON(params, JSONHelper.GETWAY_CONNECTION_URL);
        } catch (Exception e) {
            LOGGER.info("error --------------------> node conn error = {}", e.getMessage());
            return null;
        }
    }

    @RequestMapping("/node/status/{snr}")
    public String getSNRStatus(@PathVariable("snr") String snr, HttpSession session) {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("snr", snr);

            return JSONHelper.getJSON(params, JSONHelper.NODE_CX_URL);
        } catch (Exception e) {
            return null;
        }
    }

}
