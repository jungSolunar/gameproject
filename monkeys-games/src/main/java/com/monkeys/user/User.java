package com.monkeys.user;

import com.google.common.base.Objects;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by H on 2016. 11. 3..
 */
public class User {
    private WebSocketSession session;

    public User(WebSocketSession session) {
        this.session = session;
    }

    public String getId() {
        return session.getId();
    }

    public void sendMessage(String msg) throws IOException {
        TextMessage textMessage = new TextMessage(msg);
        session.sendMessage(textMessage);
    }

    public void recvMessage(String msg) {

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
        } else {
            return false;
        }

        User user = (User)obj;

        return Objects.equal(session, user.session);
    }

    @Override
    public String toString() {
        return session.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(session);
    }
}
