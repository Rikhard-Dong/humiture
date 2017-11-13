package io.ride.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午6:24
 */
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        try {
             socket = new Socket("192.168.3.2", 2333);
            // socket输出流
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            for(int i = 0; i < 5; i++) {
                writer.println("发送" + i);
                Thread.sleep(1000);
            }
            writer.println("end");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
