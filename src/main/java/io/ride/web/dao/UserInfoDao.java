package io.ride.web.dao;

import io.ride.web.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午10:09
 */
public interface UserInfoDao {
    /**
     * 验证账号是否存在
     *
     * @param username 登录名
     * @return true: 存在, false: 不存在
     */
    boolean isAccountExists(String username);

    /**
     * 验证账号
     *
     * @param username 用户名
     * @param password md5加密后的密码
     * @return true: 验证通过, false: 验证不通过
     */
    UserInfo accountValidate(@Param("username") String username,
                             @Param("password") String password);

    int addUser(@Param("user") UserInfo user);

    int updateByUsername(@Param("user") UserInfo user);

    int deleteByUserId(@Param("id") Integer id);

    int deleteByUsername(@Param("username") String username);

    UserInfo findByUserId(@Param("id") Integer id);

    UserInfo findByUsername(@Param("username") String username,
                            @Param("userType") Integer userType,
                            @Param("unitId") Integer unitId);

    UserInfo findFromUnitWithUserId(@Param("userId") Integer userId, @Param("unitId") Integer unitId);

    UserInfo findFromUnitWithUsername(@Param("username") String username, @Param("unitId") Integer unitId);

    List<UserInfo> list();

    List<UserInfo> listFromUnit(@Param("unitId") Integer unitId);

    List<UserInfo> search(@Param("arg") String arg,
                          @Param("userType") Integer userType,
                          @Param("unitId") Integer unitId);

}
