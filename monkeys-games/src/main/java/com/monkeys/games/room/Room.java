package com.monkeys.games.room;

import com.monkeys.user.User;

import java.util.Vector;

/**
 * Created by H on 2016. 11. 3..
 */
public class Room {
    Vector<User> users;

    private String name;
    private int maxUser;

    public Room(String name, int maxUser) {
        this.name = name;
        this.maxUser = maxUser;

        users = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public int getCurrentUserSize() {
        return users.size();
    }

    public boolean join(User user) {
        if(maxUser <= users.size())
            return false;

        if(isJoined(user))
            return false;

        users.add(user);
        return true;
    }

    private boolean isJoined(User user) {
        for(User u : users)
            if(user.equals(u))
                return false;

        return true;
    }

    public boolean evict(User user) {
        if(users.size() <= 0)
            return false;

        if(!isJoined(user))
            return false;

        users.remove(user);
        return true;
    }
}
