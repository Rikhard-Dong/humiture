package io.ride.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.ride.web.dao.TemperHumidDao;
import io.ride.web.dto.DataTableResult;
import io.ride.web.dto.TemperHumidDto;
import io.ride.web.entity.TemperHumid;
import io.ride.web.entity.UserInfo;
import io.ride.web.exception.HasNoPermissionException;
import io.ride.web.exception.NotFoundException;
import io.ride.web.service.QueryService;
import io.ride.web.util.PermissionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    public DataTableResult queryThByReportTime(String nodeMark, String startTime, String endTime,
                                               Integer page, Integer rows, HttpSession session)
            throws HasNoPermissionException, NotFoundException {
        UserInfo currentUser = PermissionUnit.isLogin(session);

        List<TemperHumid> ths;
        PageInfo<TemperHumid> pageInfo = null;
        if (PermissionUnit.isAuthorNode(nodeMark, currentUser.getUserType(), currentUser.getUnitId(), currentUser.getUsername())) {
            PageHelper.startPage(page, rows);
            ths = thDao.listByNodeMarkWithTime(nodeMark, startTime, endTime);
            LOGGER.info("query th by report time ths i= {}", ths);

            if (ths != null) {
                pageInfo = new PageInfo<TemperHumid>(ths);
            }
        }
        List<TemperHumidDto> dtos = new ArrayList<TemperHumidDto>();
        if (pageInfo != null) {
            LOGGER.info("query th by report time pageinfo i= {}", pageInfo);
            for (TemperHumid temperHumid : pageInfo.getList()) {
                dtos.add(new TemperHumidDto(temperHumid));
            }
        }
        if (pageInfo == null) {
            throw new NotFoundException("未发现");
        }
        return new DataTableResult(pageInfo.getTotal(), dtos);
    }
}
