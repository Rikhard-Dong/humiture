package io.ride.web.dao;

import io.ride.web.entity.THAvg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-18
 * Time: 下午6:39
 */
public interface THAvgDao {

    List<THAvg> listDayAvgForMonth(@Param("mark") String mark,
                                   @Param("year") Integer year,
                                   @Param("month") Integer month);

    List<THAvg> listMonthAvgForYear(@Param("mark") String mark,
                                    @Param("year") Integer year);

    List<THAvg> listYearAvg(@Param("mark") String mark);

}
