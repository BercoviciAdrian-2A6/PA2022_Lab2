package com.bercoviciadrianpa2022lab2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        //populate rooms
        ArrayList<Room> rooms = new ArrayList<Room>();

        rooms.add(new Room("401", Room.RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("403", Room.RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("405", Room.RoomType.COMPUTER_LAB, 30));
        rooms.add(new Room("309", Room.RoomType.HALL, 300));

        //populate events
        ArrayList<Event> events = new ArrayList<Event>();

        events.add(new Event("C1", 100, 8 , 10));
        events.add(new Event("C2", 100, 10 , 12));
        events.add(new Event("L1", 30, 8 , 10));
        events.add(new Event("L2", 30, 8 , 10));

        for (int i = 0; i < rooms.size(); i++)
        {
            System.out.println( rooms.get(i).toString() );
        }

        for (int i = 0; i < events.size(); i++)
        {
            System.out.println( events.get(i).toString() );
        }
    }
}
