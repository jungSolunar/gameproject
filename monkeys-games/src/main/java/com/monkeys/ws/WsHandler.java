package com.monkeys.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by H on 2016. 9. 26..
 */
@Controller("wsHandler")
public class WsHandler extends AbstractWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    Set<WebSocketSession> sessionSet = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSet.add(session);
        log.info("connected ws");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionSet.remove(session);
        log.info("disconnected ws");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        TextMessage msg = new TextMessage(message.getPayload());
        log.info("" + session.getId() + " -> " + message.getPayload());
        session.sendMessage(msg);
    }
}
