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

    TemperHumid findById(int temperId);

    List<TemperHumid> listByNodeMark(@Param("mark") String mark);

    List<TemperHumid> listByNodeMarkWithTime(@Param("nodeMark") String nodeMark,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);

}
