package io.ride.socket.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午5:42
 * <p>
 * 用于流的读取与输入
 */
public class SocketUtil {

    /**
     * 从流中读取数据
     *
     * @param in 读取数据的流
     * @return 读取到内容
     * @throws IOException IO异常
     */
    public static String readStr2Stream(InputStream in) throws IOException {
        String strOutput = "";
        int count = 0;
        try {
            if (in.available() != 0) {
                while (count == 0) {
                    count = in.available();
                }
                byte[] b = new byte[count];
                in.read(b);
                strOutput = DataUtil.binaryToHexString(b);
                strOutput = strOutput.toLowerCase();
            }
        } catch (IOException e) {
            throw e;
        }

        return strOutput;
    }

    /**
     * 将响应保温写入到流中放松给客户端
     *
     * @param response     响应内容
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    public static void writeStr2Stream(String response, OutputStream outputStream) throws IOException {
        try {
            BufferedOutputStream writer = new BufferedOutputStream(outputStream);
            writer.write(response.getBytes());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
