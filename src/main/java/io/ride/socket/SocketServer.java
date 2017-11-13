package io.ride.socket;

import io.ride.socket.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket启动
 * <p>
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午5:42
 */
public class SocketServer {
    private static final int PORT = 23333;
    private static int i = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);


    public static void startSocket() {
        ServerSocket server = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            server = new ServerSocket(PORT);
            LOGGER.info("socket服务器启动");
            for (; ++i > 0; ) {
                Socket request = server.accept();
                LOGGER.info("接受第{}个请求", i);
                executor.execute(new HandleDataThread(request, i));
            }
        } catch (IOException e) {
            LOGGER.error("创建ServerSocket异常 = {}", e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    LOGGER.error("关闭ServerSocket异常 = {}", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        /* 主函数, 测试使用, 网站环境中使用时放在ContextListener中 */
        ServerSocket server = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            server = new ServerSocket(PORT);
            for (int counter = 0; counter < 10; counter++) {
                Socket request = server.accept();
                LOGGER.info("接受第{}个请求", counter);
                executor.execute(new HandleDataThread(request, counter));
            }
        } catch (IOException e) {
            LOGGER.error("创建ServerSocket异常 = {}", e);
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    LOGGER.error("创建ServerSocket异常 = {}", e);
                }
            }
        }
    }
}
