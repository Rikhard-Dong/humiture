package io.ride.web.dao;

import io.ride.web.entity.TemperHumid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-4
 * Time: 下午7:42
 */
public interface TemperHumidDao {

    List<TemperHumid> list();

    List<TemperHumid> listByNodeId(int nodeId);

    TemperHumid findById(int temperId);

    TemperHumid findByIdWithNodeInfo(int temperId);

    List<TemperHumid> listByNodeIdAndTimeSlot(@Param("nodeId") int nodeId,
                                              @Param("startTime") String startTime,
                                              @Param("endTime") String endTime);

    List<TemperHumid> listByNodeMarkWithTime(@Param("nodeMark") String nodeMark,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);

}
