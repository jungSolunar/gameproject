package com.monkeys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.Vector;

/**
 * Created by H on 2016. 10. 28..
 */

@Service("rspService")
public class RSPService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Vector<WebSocketSession> users;
    private Vector<String> users_rsp;

    boolean isStarted;

    @PostConstruct
    public void init() {
        log.info("rspService init");

        users = new Vector<>();
        users_rsp = new Vector<>();

        for(int i = 0; i < 3; i++) {
            users_rsp.add("NULL");
        }

        isStarted = false;
    }

    public boolean joinUser(WebSocketSession user) throws Exception {
        if(3 <= users.size()) {
            return false;
        }

        users.add(user);

        if(3 == users.size()) {
            isStarted = true;

            for(WebSocketSession usr : users) {
                TextMessage msg = new TextMessage("start game. send me r s p");
                usr.sendMessage(msg);
            }
        }

        return true;
    }

    public void evictUser(WebSocketSession user) {
        try {
            users.remove(user);
        } catch (Exception e) {

        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    private String getWinner(Vector<WebSocketSession> users) {
        int r = 0;
        int s = 0;
        int p = 0;

        for(String rsp : users_rsp) {
            if(rsp.equals("r")) {
                ++r;
            } else if(rsp.equals("s")) {
                ++s;
            } else {
                ++p;
            }
        }

        if(1 == r && 1 == s && 1 == p) {
            return null;
        }

        if(3 == r || 3 == s && 3 == p) {
            return null;
        }


        if(0 < r && 0 < s) {
            return "r";
        }
        if(0 < s && 0 < p) {
            return "s";
        }
        if(0 < p && 0 < r) {
            return "p";
        }

        return null;
    }

    public void choiceRSP(WebSocketSession user, String rsp) throws Exception{
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).equals(user)) {
                if(users_rsp.get(i).equals("NULL")) {
                    // input
                    if(rsp.equals("r") || rsp.equals("s") || rsp.equals("p")) {
                        users_rsp.set(i, rsp);
                    } else {
                        TextMessage msg = new TextMessage("choice r s p");
                        user.sendMessage(msg);
                    }
                } else {
                    // already
                    TextMessage msg = new TextMessage("already choice");
                    user.sendMessage(msg);
                }

                break;
            }
        }

        boolean isReady = true;
        for(int i = 0; i < users_rsp.size(); i++) {
            if(users_rsp.get(i).equals("NULL")) {
                isReady = false;
                break;
            }
        }

        if(isReady) {
            String winner = getWinner(users);

            if(null == winner) {
                for(int i = 0; i < users.size(); i++) {
                    TextMessage msg = new TextMessage("bgim");
                    users.get(i).sendMessage(msg);

                    users_rsp.set(i, "NULL");
                }

                return;
            }

            for(int i = 0; i < users.size(); i++) {
                if(users_rsp.get(i).equals(winner)) {
                    TextMessage msg = new TextMessage("you win!!");
                    users.get(i).sendMessage(msg);
                } else {
                    TextMessage msg = new TextMessage("you lose!");
                    users.get(i).sendMessage(msg);
                }

                users_rsp.set(i, "NULL");
            }
        } else {
            // nop
        }
    }
}
