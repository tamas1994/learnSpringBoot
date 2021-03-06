package com.folkcat.learnspringboot.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tamas on 2017/12/12.
 */

@Controller
@RequestMapping(value="ws")
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("")
    public String index() {
        return "ws";
    }

    @MessageMapping("send")
    @SendTo("topic/send")
    public SocketMessage send(SocketMessage message) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.date = df.format(new Date());
        System.out.println("send 执行了哦");
        return message;
    }

    @Scheduled(fixedRate = 1000)
    @SendTo("topic/callback")
    public Object callback() throws Exception {
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
    }


}
