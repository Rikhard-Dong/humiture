package io.ride.socket.util;

import java.util.Arrays;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午7:46
 */
public class CRCUtil {
    public static boolean check(String reqStr) {
        boolean flag = false;
        String data = DataUtil.string2HexString(DataUtil.getValueByCode(reqStr, "cla")) +
                DataUtil.getValueByCode(reqStr, "data");
        String crc = DataUtil.getValueByCode(reqStr, "crc");
        String c = (CRC16Util.getCRC16(DataUtil.hexString2Bytes(data)));
        if (c.length() == 3) {
            c = "0" + c;
        }
        if (crc.equalsIgnoreCase(c)) {
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(CRCUtil.check("7B77640805010206040100000100172B22854E7D"));
    }
}
