package io.ride.socket.util;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午8:17
 * crc16校验码工具
 */
public class CRC16Util {
    public static String getCRC16(byte[] bytes) {
/*        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        for (int i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (int j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }

        return Integer.toHexString(CRC);*/
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }
}
