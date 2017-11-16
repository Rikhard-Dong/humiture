package io.ride.socket;

import io.ride.socket.single.SingletonPattern;
import io.ride.socket.util.CRCUtil;
import io.ride.socket.util.DataUtil;
import io.ride.socket.util.ReceiverStringUtil;
import io.ride.socket.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-6
 * Time: 下午5:48
 * <p>
 * 数据处理句柄
 */
public class HandleDataThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleDataThread.class);

    private Socket request;
    private int requestId;

    // 解析后数据
    List<String> splitRtn = new ArrayList<String>();

    // 单例
    private SingletonPattern singleton = SingletonPattern.getInstance();

    public HandleDataThread(Socket request, int requestId) {
        this.request = request;
        this.requestId = requestId;
    }

    public void run() {
        LOGGER.info("连接成功, 准备接受数据");
        try {
            request.setSoTimeout(20000);
            // 接受客户端发来的数据
            while (true) {
                String hexStr;
                String requestStr;
                try {
                    hexStr = SocketUtil.readStr2Stream(request.getInputStream());
                    if (!hexStr.equals("")) {
                        requestStr = DataUtil.hexString2String(hexStr);

                        LOGGER.info("{}号客户端返回的十六进制内容为{}", requestId, hexStr);
                        LOGGER.info("{}号客户端返回内容转换后为{}", requestId, requestStr);
                    }

                } catch (SocketTimeoutException e) {
                    LOGGER.error("超时, requestId={}已关闭", requestId);
                    break;
                }

                if (!hexStr.equals("")) {
                    splitRtn = ReceiverStringUtil.splitRtnStr(hexStr);

                    // 对接收到的数据进行处理
                    for (String reqStr : splitRtn) {
                        if (CRCUtil.check(reqStr)) {
                            /*
                            * '7374' --> st 设置
                            * '6378' --> cx 查询
                            * '6371' --> cq 重启
                            * '7362' --> sb 上报
                            **/
                            if (reqStr.contains("7374") || reqStr.contains("6378") ||
                                    reqStr.contains("6371") || reqStr.contains("7362")) {
                                singleton.add(reqStr);
                            }
                            String cla = DataUtil.getValueByCode(reqStr, "cla");
                            String dataStr = DataUtil.getValueByCode(reqStr, "data");
                            LOGGER.info("clr={}", cla);
                            if (cla.equals("xt")) {
                                // LOGGER.info("接收到一个心跳包");
                                // 心跳包
                                // 响应客户端
                                ReceiverStringUtil.writeString("XT", request);
                                ReceiverStringUtil.updateOnlineSocket(singleton, dataStr, request);
                            } else if (cla.equals("cq")) {
                                // TODO 重启
                            } else if (cla.equals("sb")) {
                                // TODO 上报
                            } else if (cla.equals("wd")) {
                                // TODO 温度
                                ReceiverStringUtil.updateNowAndAllTH(singleton,dataStr);
                                ReceiverStringUtil.writeString("WD", request);
                            } else if (cla.equals("by")) {
                                // TODO 是否启用备用
                            } else if (cla.equals("cx")) {
                                // TODO 查询
                            } else if (cla.equals("st")) {
                                // TODO
                            }
                        } else {
                            LOGGER.warn("校验失败");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("socket异常={}", e);
        } catch (IOException e) {
            LOGGER.error("IO异常={}", e);
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (IOException e) {
                    LOGGER.error("关闭socket失败 = {}", e);
                }
            }
        }
    }
}
