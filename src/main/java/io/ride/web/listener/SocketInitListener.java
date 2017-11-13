package io.ride.web.listener;

import io.ride.socket.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午5:54
 */
public class SocketInitListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketInitListener.class);

    /* 初始 */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("启动socket服务器!");
        Thread t = new StartThread();
        t.start();
    }

    /* 销毁 */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public class StartThread extends Thread {
        @Override
        public void run() {
            SocketServer.startSocket();
        }
    }
}
