package com.monkeys.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by H on 2016. 9. 26..
 */
@Controller("wsHandler")
public class WsHandler extends AbstractWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    Set<WebSocketSession> sessionSet;

    @PostConstruct
    public void init() {
        log.info("wsHandler PostConstruct");
        sessionSet = new HashSet<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session);
        log.info("connected ws - " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionSet.remove(session);
        log.info("disconnected ws - " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        TextMessage msg = new TextMessage(message.getPayload());
        session.sendMessage(msg);
    }
}
