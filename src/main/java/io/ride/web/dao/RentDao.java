package io.ride.web.dao;

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
}
