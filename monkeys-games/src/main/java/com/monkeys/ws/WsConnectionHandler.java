package com.monkeys.ws;

import com.monkeys.service.RSPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by H on 2016. 9. 26..
 */
@Controller("wsConnHandler")
public class WsConnectionHandler extends AbstractWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final long PRUNING_PERIOD = 5000L;

    private Timer timer;
    private TimerTask connPruner;

    private Set<WebSocketSession> sessionSet;

    @Autowired
    private RSPService rspService;

    @PostConstruct
    public void init() {
        log.info("wsHandler PostConstruct");
        sessionSet = new HashSet<>();

        timer = new Timer();
        connPruner = new ConnectionPruner();
        timer.schedule(connPruner, 0, PRUNING_PERIOD);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connected ws - " + session.getId());
        sessionSet.add(session);

        if(!rspService.joinUser(session)) {
            log.info("room is full");
            TextMessage msg = new TextMessage("room is full");
            session.sendMessage(msg);
            session.close();

            return;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionSet.remove(session);
        rspService.evictUser(session);
        log.info("disconnected ws - " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //TextMessage msg = new TextMessage(message.getPayload());
        //session.sendMessage(msg);

        if(rspService.isStarted()) {
            rspService.choiceRSP(session, message.getPayload());
        }
    }

    private class ConnectionPruner extends TimerTask {
        @Override
        public void run() {
            // not active connection pruning
        }
    }
}
