package io.ride.web.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-9
 * Time: 下午9:07
 */
public interface UserAuthorDao {
    int add(@Param("userId") Integer userId,
            @Param("nodeId") Integer nodeId);

    int delete(@Param("userId") Integer userId,
               @Param("nodeId") Integer nodeId);

    int deleteById(@Param("userAuthorId") Integer userAuthorId);


}
