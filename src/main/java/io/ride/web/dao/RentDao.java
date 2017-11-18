package io.ride.web.dao;

import com.sun.org.apache.regexp.internal.RE;
import io.ride.web.entity.Rent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 上午10:38
 */
public interface RentDao {

    /**
     * 查询所有租赁信息
     *
     * @return
     */
    List<Rent> list();

    /**
     * 查询本单位租赁信息
     *
     * @return
     */
    List<Rent> listWithUnit(@Param("unitId") Integer uintId);

    /**
     * 添加租赁信息
     *
     * @param rent
     * @return
     */
    int add(@Param("rent") Rent rent);

    /**
     * 更新租赁信息
     *
     * @param rent
     * @return
     */
    int update(@Param("rent") Rent rent);

    /**
     * @param id
     * @return
     */
    int updateStatus(@Param("id") Integer id);

    /**
     * 删除租赁信息
     *
     * @param id
     * @return
     */
    int delete(@Param("id") Integer id);

    Rent findByTime(@Param("startTime") String startTime,
                    @Param("endTime") String endTime);


    Rent findByRentId(@Param("rentId") int rentId);

    /**
     * 根据网关Id和单位title查询当前时间内的租约信息
     *
     * @param getwayId
     * @param title
     * @return
     */
    Rent findByGetwayIdAndUnitTileAndCurrentTime(@Param("getwayId") Integer getwayId,
                                                 @Param("title") String title);

    /**
     * 查询该特权单位在该时间段内是否有租用信息
     *
     * @param unitId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Rent> findByUnitIdAndStartTimeAndEndTime(@Param("unitId") Integer unitId,
                                                  @Param("startTime") String startTime,
                                                  @Param("endTime") String endTime);

    List<Rent> findByUnitTypeAndStartTimeAndEndTime(@Param("startTime") String statTime,
                                                    @Param("endTime") String endTime);


    /**
     * 根据mark和unitId判断当前单位是否对该网关由租赁关系
     *
     * @param mark
     * @param id
     * @return
     */
    Rent findByNodeMarkAndUnitId(@Param("mark") String mark,
                                 @Param("unitId") Integer id);

    List<Rent> listWithMark(@Param("mark") String mark);
}
