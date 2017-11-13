package io.ride.web.service.impl;

import io.ride.web.dao.NodeDao;
import io.ride.web.dao.TemperHumidDao;
import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.TemperHumidityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午8:25
 */
@Service(value = "temperHumidityService")
public class TemperHumidityServiceImpl implements TemperHumidityService {
    private static Logger LOGGER = LoggerFactory.getLogger(TemperHumidityServiceImpl.class);

    @Autowired
    private TemperHumidDao temperHumidDao;

    @Autowired
    private NodeDao nodeDao;

    public List<TemperHumid> listAllTempersAndHumiditys() {
        return temperHumidDao.list();
    }

    public List<TemperHumid> listNodeTHWithTime(String nodeMark, String startTime, String endTime, HttpSession session)
            throws HasNoPermissionException, NotFoundException {

        if(nodeDao.findByMark(nodeMark) ==null) {
            // TODO
        }

        return null;
    }
}
