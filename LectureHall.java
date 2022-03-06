package com.bercoviciadrianpa2022lab2;

public class LectureHall extends Room
{
    private boolean hasProjector;

    LectureHall (String roomName, int capacity, boolean hasProjector)
    {
        super(roomName,capacity);
        this.hasProjector = hasProjector;
    }

    @Override
    public String toString()
    {
        String projectorStatus = "projector available";

        if (hasProjector == false)
            projectorStatus = "projector NOT available";

        return "Lecture hall " + roomName + " - capacity: " + capacity;// + " - " + projectorStatus;
    }
}
