package io.ride.web.util;

import org.junit.Test;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午1:38
 */
public class MD5EncryptTest {
    @Test
    public void encrypt() throws Exception {
        System.out.println(MD5Encrypt.encrypt("admin"));
    }

}