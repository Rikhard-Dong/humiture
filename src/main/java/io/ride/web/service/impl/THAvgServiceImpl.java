package io.ride.web.service.impl;

import io.ride.web.dao.GetwayDao;
import io.ride.web.dao.NodeDao;
import io.ride.web.dao.THAvgDao;
import io.ride.web.entity.Getway;
import io.ride.web.entity.Node;
import io.ride.web.entity.THAvg;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.THAvgService;
import io.ride.web.util.PermissionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午7:36
 */
@Service("tHavgService")
public class THAvgServiceImpl implements THAvgService {

    @Autowired
    private THAvgDao thAvgDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private GetwayDao getwayDao;

    public List<THAvg> listDayAvgForMonth(String mark, Integer year, Integer month, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        PermissionUnit.isLogin(session);

        return thAvgDao.listDayAvgForMonth(mark, year, month);
    }

    public List<THAvg> listMonthAvgForYear(String mark, Integer year, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        PermissionUnit.isLogin(session);

        return thAvgDao.listMonthAvgForYear(mark, year);
    }

    public List<THAvg> listYearAvg(String mark, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        PermissionUnit.isLogin(session);

        return thAvgDao.listYearAvg(mark);
    }

    public Map<String, Object> nodeInfo(String mark, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);
        Node node = nodeDao.findByMark(mark, currentUser.getUserType(), currentUser.getUnitId());
        if (node == null) {
            throw new NotFoundException("节点未发现");
        }
        boolean isGetway = node.getType() == 0;
        List<String> subMark = new ArrayList<String>();
        String getwayMark;
        if (isGetway) {
            getwayMark = node.getNodeMark();
            List<Node> nodes = getwayDao.listSubNodeByNodeMark(getwayMark);
            for (Node subNode : nodes) {
                subMark.add(subNode.getNodeMark());
            }
        } else {
            Getway getway = getwayDao.findById(node.getGetwayId(), currentUser.getUserType(),
                    currentUser.getUnitId());
            if (getway == null) {
                throw new NotFoundException("网关不存在");
            }
            getwayMark = getway.getGetwayMark();
            subMark.add(node.getNodeMark());

        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isGetway", isGetway);
        map.put("getwayMark", getwayMark);
        map.put("subMarks", subMark);
        map.put("success", true);
        return map;
    }
}
