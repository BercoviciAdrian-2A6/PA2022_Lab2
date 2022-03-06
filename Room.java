package com.bercoviciadrianpa2022lab2;

import java.util.Objects;

public abstract class Room
{
    //non-static variables
    protected String roomName;
    protected int capacity;
    //Constructor

    public Room(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
    }

    //methods

    //force override in each subclass
    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null /*|| getClass() != o.getClass()*/ )//this is commented out because the desired behaviour should be able to compare labs and CourseHalls
            return false;

        Room room = (Room) o;
        return roomName.equals(room.roomName);//room name is the unique identifier
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
