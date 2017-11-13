package io.ride.web.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-10
 * Time: 下午7:03
 */
public class ParamDivisionUtilTest {

    @Test
    public void getParams() throws Exception {
        String[] params = ParamDivisionUtil.getParams("1001-1002-1003");
        System.out.println(Arrays.toString(params));

    }

}