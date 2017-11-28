package io.ride;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-26
 * Time: 下午8:17
 */
public class OkHttpTest {

    @Test
    public void testGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.28.44.241:8888/data/node/tree").build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
