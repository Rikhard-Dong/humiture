package io.ride.web.dao;

import io.ride.web.entity.Getway;
import io.ride.web.entity.Unit;
import io.ride.web.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-7
 * Time: 下午9:03
 */
public interface UnitDao {
    List<Unit> list();

    List<UserInfo> listUnitUser(@Param("id") Integer id);

    List<Getway> listRentingGetway(@Param("title") String title);

    int add(@Param("unit") Unit unit);

    int update(@Param("unit") Unit unit);

    int deleteById(@Param("id") Integer id);

    int deleteByTitle(@Param("title") String title);

    Unit findById(@Param("id") Integer id);

    Unit findByTitle(@Param("title") String title);

    boolean isExists(@Param("id") int id);

    List<Unit> search(@Param("arg") String arg);

}
