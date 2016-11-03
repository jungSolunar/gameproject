package com.monkeys.ws;

import com.monkeys.games.GameService;
import com.monkeys.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by H on 2016. 9. 26..
 */
@Controller("wsConnHandler")
public class WsConnectionHandler extends AbstractWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final long PRUNING_PERIOD = 5000L;

    private Timer timer;
    private TimerTask connPruner;

    private Set<User> userSet;

    private Map<String, GameService> gameMap;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        log.info("wsHandler PostConstruct");

        userSet = new HashSet<>();
        gameMap = new HashMap<>();

        timer = new Timer();
        connPruner = new ConnectionPruner();
        timer.schedule(connPruner, 0, PRUNING_PERIOD);

        gameMap = context.getBeansOfType(GameService.class);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connected ws - " + session.getId());

        User user = new User(session);
        userSet.add(user);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSet.remove(new User(session));

        log.info("disconnected ws - " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(User user : userSet) {
            if(user.getId().equals(session.getId())) {
                user.recvMessage(message.getPayload());
                break;
            }
        }
    }

    private class ConnectionPruner extends TimerTask {
        @Override
        public void run() {

        }
    }
}
