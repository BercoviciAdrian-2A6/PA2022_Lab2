package com.bercoviciadrianpa2022lab2;

public class Laboratory extends Room
{
    enum OS
    {
        WINDOWS,
        LINUX,
        MAC
    }

    private OS computersOS;

    Laboratory (String roomName, int capacity, OS computersOS)
    {
        super(roomName,capacity);
        this.computersOS = computersOS;
    }

    public String toString()
    {
        return "Laboratory " + roomName + " - capacity: " + capacity;// + " - OS: " + computersOS.toString();
    }
}
