package com.bercoviciadrianpa2022lab2;

public class Event
{
    //non-static variables
    private String eventName;
    private int participantsNumber;
    private int startTime, endTime;
    //Constructors

    public Event(String eventName, int participantsNumber, int startTime, int endTime) {
        this.eventName = eventName;
        this.participantsNumber = participantsNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Methods

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", participantsNumber=" + participantsNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
