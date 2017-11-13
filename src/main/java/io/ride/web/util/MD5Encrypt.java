package io.ride.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午1:31
 */
public class MD5Encrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Encrypt.class);

    public static final String encrypt(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }
}
