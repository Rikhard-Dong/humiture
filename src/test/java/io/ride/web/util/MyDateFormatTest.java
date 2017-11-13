package io.ride.web.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-5
 * Time: 下午3:02
 */
public class MyDateFormatTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDateFormatTest.class);

    @Test
    public void format() throws Exception {
        Date date = new Date();
        LOGGER.info("format={}", MyDateFormat.format(date));
    }

}