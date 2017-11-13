package io.ride.web.dao;

import io.ride.web.entity.Repair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-13
 * Time: 下午3:26
 */
public interface RepairDao {
    int add(@Param("repair") Repair repair);

    List<Repair> list(@Param("userType") Integer userType,
                      @Param("unitId") Integer unitId);

    int update(@Param("repair") Repair repair);

    int updateStatus(@Param("repairId") Integer repairId, @Param("status") Integer status);

    int delete(@Param("repaitId") Integer repairId);

    List<Repair> search(@Param("param") String param,
                        @Param("userType") Integer userType,
                        @Param("unitId") Integer unitId);

    Repair findById(@Param("repairId") Integer repairId,
                    @Param("userType") Integer userType,
                    @Param("unitId") Integer unitId);

}
