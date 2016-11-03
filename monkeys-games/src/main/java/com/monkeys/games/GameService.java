package com.monkeys.games;

import com.google.common.collect.Maps;
import com.monkeys.games.room.Room;
import com.monkeys.user.User;

import java.util.Map;

/**
 * Created by H on 2016. 11. 3..
 */
public abstract class GameService {
    private int createRoomNumber;

    private int maxUser;

    protected Map<Integer, Room> rooms;

    public GameService() {
        createRoomNumber = 1;
        maxUser = 0;
        rooms = Maps.newHashMap();
    }

    public abstract String getServiceName();

    public boolean createRoom(String name) { if(rooms.containsKey(createRoomNumber)) {
            return false;
        }

        rooms.put(createRoomNumber++, new Room(name, maxUser));
        return true;
    }

    public void deleteRoom(int roomNumber) {
        try {
            rooms.remove(roomNumber);
        } catch(NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public int getRoomUserSize(int roomNumber) {
        Room room = rooms.get(roomNumber);

        return room.getCurrentUserSize();
    }

    public boolean join(User user, int roomNumber) {
        Room room = rooms.get(roomNumber);

        if(room.join(user)) {

        } else {

        }

        return false;
    }

    public boolean evict(User user, int roomNumber) {
        Room room = rooms.get(roomNumber);

        if(room.evict(user)) {

        } else {

        }

        return false;
    }
}
