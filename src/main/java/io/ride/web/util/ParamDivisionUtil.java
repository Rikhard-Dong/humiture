package io.ride.web.util;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-10
 * Time: 下午7:03
 */
public class ParamDivisionUtil {
    public static String[] getParams(String paramsStr) {
        return paramsStr.split("_");
    }
}
