package io.ride.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午6:37
 */
public class DataUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);

    private static final String hexStr = "0123456789ABCDEF";

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes 待转换字符串
     * @return 转换后的结果
     */
    public static String binaryToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        String hex;
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result.append(hex);
        }

        return result.toString();
    }

    /**
     * 将16进制转换为字符串
     *
     * @param hexStr 待转换16进制
     * @return 转换后字符串
     */
    public static String hexString2String(String hexStr) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexStr.length() / 2; i++) {
            result.append((char) Integer.valueOf(hexStr.substring(i * 2, i * 2 + 2), 16).byteValue());
        }

        return result.toString();
    }

    /**
     * 根据不同的代码得到不同的值
     *
     * @param reqStr 报文
     * @param code   需要得到信息内容的代码
     * @return 代码对应的值
     */
    public static String getValueByCode(String reqStr, String code) {
        String repStr = "";
        if (reqStr.length() >= 6) {
            String head = reqStr.substring(0, 2);
            String cla = reqStr.substring(2, 6);
            String data = reqStr.substring(6, reqStr.length() - 6);
            String crc = reqStr.substring(reqStr.length() - 6, reqStr.length() - 2);
            String end = reqStr.substring(reqStr.length() - 2, reqStr.length());
            //LOGGER.info("解析保温内容(十六进制)\n{}\n\t{}\n\t{}\n\t{}\n{}", head, cla, data, crc, end);
            /*LOGGER.info("解析保温内容\n{}\n\t{}\n\t{}\n\t{}\n{}", DataUtil.hexString2String(head),
                    DataUtil.hexString2String(cla), DataUtil.hexString2String(data),
                    DataUtil.hexString2String(crc), DataUtil.hexString2String(end));
            */
            // 根据代码返回不同信息
            if (code.equals("head")) {
                repStr = DataUtil.hexString2String(head);
            } else if (code.equals("cla")) {
                repStr = DataUtil.hexString2String(cla);
            } else if (code.equals("data")) {
                repStr = data;
            } else if (code.equals("crc")) {
                repStr = crc;
            } else if (code.equals("end")) {
                repStr = DataUtil.hexString2String(end);
            }
        }


        return repStr;
    }

    /**
     * 将字符串转换为16进制字符串
     *
     * @param str 待转换字符串
     * @return 转化为16进制后的字符串
     */
    public static String string2HexString(String str) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int ch = (int) str.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }

        return hexString.toString();
    }

    /**
     * 将十六进制的字符串转换为字节数组
     *
     * @param hexString 待转换的十六进制字符串
     * @return 转换后的字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) (char2Byte(hexChars[i * 2]) << 4 | char2Byte(hexChars[i * 2 + 1]));
        }
        return result;
    }

    /**
     * 将字符转换为字节
     *
     * @param c 待转换字符
     * @return 转换后的字节
     */
    private static byte char2Byte(char c) {
        return (byte) hexStr.indexOf(c);
    }
}
