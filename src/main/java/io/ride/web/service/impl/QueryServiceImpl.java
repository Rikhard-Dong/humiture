package io.ride.web.service.impl;

import io.ride.web.dao.TemperHumidDao;
import io.ride.web.entity.TemperHumid;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-11
 * Time: 下午7:59
 * <p>
 * 提供数据查询服务
 */
@Service("queryService")
public class QueryServiceImpl implements QueryService {
    private static Logger LOGGER = LoggerFactory.getLogger(QueryServiceImpl.class);


    @Autowired
    private TemperHumidDao thDao;

    public List<TemperHumid> queryThByReportTime(String nodeMark, String startTime, String endTime, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        // TODO 权限处理

        return thDao.listByNodeMarkWithTime(nodeMark, startTime, endTime);
    }
}
