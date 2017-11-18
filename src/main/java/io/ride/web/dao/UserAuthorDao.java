package io.ride.web.dao;

import io.ride.web.entity.UserAuthor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    boolean isExists(@Param("userId") Integer userId,
                 @Param("nodeId") Integer nodeId);

    List<UserAuthor> list(@Param("unitId") Integer unitId);

}
