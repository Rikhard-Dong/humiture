package io.ride.socket.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午8:44
 */
public class CRC16UtilTest {
    @Test
    public void getCRC16() throws Exception {
        System.out.println(CRC16Util.getCRC16(
                DataUtil.hexString2Bytes("7b77640805010206040100000100172b22854e7d")));
    }

}