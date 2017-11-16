package io.ride.web.controller;

import io.ride.socket.SendTread;
import io.ride.socket.entity.Message;
import io.ride.socket.util.CRC16Util;
import io.ride.socket.util.DataUtil;
import io.ride.web.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-14
 * Time: 下午9:14
 */
@Controller
@RequestMapping
@ResponseBody
public class CommandController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandController.class);

    @RequestMapping("/command/{op}")
    public Result runCommand(@PathVariable(value = "op") String op,
                             @RequestParam(value = "snr") String snr,
                             HttpSession session) {
        Result result = new Result();
        if (op.equals("cq")) {
            String crcStr = CRC16Util.getCRC16(DataUtil.hexString2Bytes(DataUtil.string2HexString(snr)));
            String req = "{CQ" + crcStr + "}";
            SendTread sendTread = new SendTread(snr, op, new Message(req));
            Thread thread = new Thread(sendTread);
            thread.start();
            boolean flag = false;
            try {
                thread.join();
                flag = sendTread.getResult();
            } catch (InterruptedException e) {
                LOGGER.error("command control error ----> {}", e.getMessage());
            }
            if (flag) {
                result.setCode(1);
                result.setSuccess(true);
                result.setMessage("重启成功!");
                LOGGER.info("command control reboot success");
            } else {
                result.setCode(-1);
                result.setSuccess(false);
                result.setMessage("重启失败!请重启!");
                LOGGER.info("command control reboot fail");
            }

        } else if (op.equals("")) {

        } else {
            result.setCode(-1);
            result.setSuccess(false);
            result.setMessage("未知操作!");
        }
        return result;
    }
}
