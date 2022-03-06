package com.bercoviciadrianpa2022lab2;

import java.time.LocalTime;

public class Event
{
    //non-static variables
    private String eventName;
    private int participantsNumber;
    private int dayOfWeek;
    private LocalTime startTime, endTime;
    private Room associatedRoom;
    //Constructors

    public Event(String eventName, int participantsNumber,int dayOfWeek, int startTime, int endTime) {
        this.eventName = eventName;
        this.participantsNumber = participantsNumber;
        this.dayOfWeek = dayOfWeek;
        this.startTime = LocalTime.of(startTime, 0);
        this.endTime = LocalTime.of(endTime, 0);
    }

    //Methods

    public static boolean eventsOverlap(Event eventA, Event eventB)
    {
        if (eventA.dayOfWeek != eventB.dayOfWeek)
            return false;

        if ( eventA.startTime.compareTo(eventB.startTime) >= 0 && eventA.startTime.compareTo(eventB.endTime) < 0 )
            return true;

        if ( eventB.startTime.compareTo(eventA.startTime) >= 0 && eventB.startTime.compareTo(eventA.endTime) < 0 )
            return true;

        //overlapping means one event starts while the other is still ongoing

        return false;
    }

    @Override
    public String toString() {
        String dayOfWeekStr = "";

        switch ( dayOfWeek )
        {
            case 0:{  dayOfWeekStr = "Mon"; break;}
            case 1:{  dayOfWeekStr = "Tue"; break;}
            case 2:{  dayOfWeekStr = "Wed"; break;}
            case 3:{  dayOfWeekStr = "Thr"; break;}
            case 4:{  dayOfWeekStr = "Fri"; break;}
        }

        return eventName + " ( " + dayOfWeekStr + " " + startTime.getHour() + "-" + endTime.getHour() + ")";
    }

    public static int compare( Event A, Event B)
    {
        if (A.dayOfWeek < B.dayOfWeek)
            return -1;

        if (B.dayOfWeek < A.dayOfWeek)
            return 1;

        return A.startTime.compareTo(B.startTime);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Event event = (Event) o;
        return eventName.equals(event.eventName);
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public Room getAssociatedRoom() {
        return associatedRoom;
    }

    public void setAssociatedRoom(Room associatedRoom) {
        this.associatedRoom = associatedRoom;
    }
}
