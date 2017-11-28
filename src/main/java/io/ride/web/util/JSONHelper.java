package io.ride.web.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-21
 * Time: 下午1:24
 * <p>
 * 获取远程json数据
 */
public class JSONHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(JSONHelper.class);

    private static String IP = "101.132.163.95";

    public static String NODE_SET_URL;              // 手自动可切换控制
    public static String GETWAY_INFO_URL;           // 网关数据信息获取
    public static String GETWAY_CONNECTION_URL;     // 网关连接状态获取
    public static String NODE_INFO_URL;             // 节点数据信息获取
    public static String NODE_CX_URL;               // 继电器状态获取

    static {
        NODE_SET_URL = "http://" + IP + "/TemperHumid/NodeServ?op=nodeset";
        GETWAY_INFO_URL = "http://" + IP + "/TemperHumid/TemperHumidServ?op=nowgtth";
        GETWAY_CONNECTION_URL = "http://" + IP + "/TemperHumid/GetWayServ?op=getonlinegt";
        NODE_INFO_URL = "http://" + IP + "/TemperHumid/TemperHumidServ?op=now";
        NODE_CX_URL = "http://" + IP + "/TemperHumid/NodeServ?op=nodecx";
    }

    public static void setIP(String IP) {
        JSONHelper.IP = IP;
    }

    public static String getJSON(Map<String, Object> map, String toUrl) {
        JSONArray jsonObject = null;
        OutputStreamWriter out = null;
        StringBuffer buffer = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.append("&" + entry.getKey() + "=" + entry.getValue());
            }
            LOGGER.info("请求url ------ >" + toUrl + params);
            // 1. 建立连接
            // 创建链接URL
            URL url = new URL(toUrl + params);
            // http传输
            HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();

            httpURLConn.setDoOutput(true);
            httpURLConn.setDoInput(true);
            httpURLConn.setUseCaches(false);

            // 设置请求方式
            httpURLConn.setRequestMethod("GET");
            httpURLConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            // 获取数据
            InputStream inputStream = httpURLConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader reader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            reader.close();
            inputStream = null;
            httpURLConn.disconnect();
            if (buffer.toString().startsWith("[")) {
                jsonObject = new JSONArray(buffer.toString());
            } else {
                jsonObject = new JSONArray("[" + buffer.toString() + "]");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("getwayId", 4);
        try {
            String result = getJSON(params, NODE_INFO_URL);
            System.out.println("------------------------------------------------------------");
            System.out.println(result);
            System.out.println("------------------------------------------------------------");
            JSONArray json = new JSONArray(result);
            JSONObject[] objects = new JSONObject[json.length()];
            for (int i = 0; i < json.length(); i++) {
//                objects[i] = new JSONObject(json.get(i).toString());
                objects[i] = json.optJSONObject(i);
                System.out.println("------------------------------------------------------------");
                System.out.println(objects[i]);
                System.out.println(objects[i].get("nodenum"));
                System.out.println("------------------------------------------------------------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
