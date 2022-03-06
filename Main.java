package com.bercoviciadrianpa2022lab2;

import java.util.ArrayList;

/**
 * @author Bercovici Adrian 2A6
 * PA 2022, Laboratory 2
 */

public class Main {

    public static void main(String[] args)
    {

        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Event> events = new ArrayList<Event>();

        boolean generateRandomData = true;

        if (generateRandomData == false) {
            rooms.add(new LectureHall("309", 100, true));
            rooms.add(new LectureHall("309", 200, false));//intentional duplicate
            rooms.add(new Laboratory("401", 30, Laboratory.OS.WINDOWS));
            rooms.add(new Laboratory("403", 30, Laboratory.OS.LINUX));
            rooms.add(new Laboratory("405", 30, Laboratory.OS.MAC));
            rooms.add(new Laboratory("405", 45, Laboratory.OS.MAC));

            events.add(new Event("C1", 100, 1, 8, 10));
            events.add(new Event("C2", 100, 1, 10, 12));
            events.add(new Event("C2", 100, 1, 10, 12));//intentional duplicate
            events.add(new Event("L1", 30, 1, 8, 10));
            events.add(new Event("L2", 30, 1, 8, 10));
            events.add(new Event("L2", 30, 1, 8, 10));//intentional duplicate
        }
        else
        {
            InputGenerator inputGenerator = new InputGenerator( 5, 2, 6 );
            rooms = inputGenerator.getRooms();
            events = inputGenerator.getEvents();
        }

        Scheduler scheduler = new Scheduler(rooms, events);

        scheduler.applyGraphColoring(1);

        System.out.print("\n");

        scheduler.applyGraphColoring(2);

    }
}
