package io.ride.web.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
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
            // 1. 建立连接
            // 创建链接URL
            URL url = new URL(toUrl);
            // http传输
            HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();

            httpURLConn.setDoOutput(true);
            httpURLConn.setDoInput(true);
            httpURLConn.setUseCaches(false);

            // 设置请求方式
            httpURLConn.setRequestMethod("GET");
            httpURLConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            // 2.传入参数
            // 得到请求的输出流对象
            out = new OutputStreamWriter(httpURLConn.getOutputStream(), "UTF-8");
            // 把数据写入
            StringBuffer params = new StringBuffer();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.append("&" + entry.getKey() + "=" + entry.getValue());
            }
            LOGGER.info("请求参数 ------ >" + params);
            out.append(params.toString());
            out.flush();
            out.close();

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
            jsonObject = new JSONArray(buffer.toString());
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
            System.out.println(getJSON(params, NODE_INFO_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
