package io.ride.web.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JSONHelperTest {

    @Test
    public void getJSON() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("getwayId", 3);
        String result = JSONHelper.getJSON(map, JSONHelper.NODE_INFO_URL);
        System.out.println(result);
    }
}