package com.bercoviciadrianpa2022lab2;

public class Room
{
    //static variables & types
    enum RoomType
    {
        HALL,
        COMPUTER_LAB
    };

    //non-static variables
    private String roomName;
    private RoomType roomType;
    private int capacity;
    //Constructor

    public Room(String roomName, RoomType roomType, int capacity) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.capacity = capacity;
    }

    //methods

    @Override
    public String toString() {
        return "Room{" +
                "roomName='" + roomName + '\'' +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                '}';
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
