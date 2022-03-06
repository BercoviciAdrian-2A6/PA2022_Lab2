package com.bercoviciadrianpa2022lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

/**
 * This class is used to generate larger inputs
 * The rooms will always be generated in a large enough number to ensure a valid solution exists
 * The scheduler will try to minimize the amount of rooms used
 */

public class InputGenerator
{
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Event> events = new ArrayList<Event>();

    /**
     *
     * @param subjectsNumber The number of subjects ex: Math, OOP, DataStructs etc..
     * @param seriesNumber The number of series ex A, B, C...
     * @param groupsNumber The number of groups in each series ex A1, A2 etc...
     */
    InputGenerator(int subjectsNumber, int seriesNumber/*A,B,C,D..*/,int groupsNumber/*A1,A2,A3....*/)
    {
        ArrayList<String> subjects = getSubjects(subjectsNumber);
        ArrayList<String> series = getSeries(seriesNumber);
        ArrayList<String> groups = getGroups(series, groupsNumber);

        Random random = new Random();

        for (int i = 0; i < subjects.size(); i++)
        {

            for (int j = 0; j < series.size(); j++)//generate lectures and consultations
            {
                String lecture = subjects.get(i) + "_lecture_" + series.get(j);
                String consultation = subjects.get(i) + "_consultation_" + series.get(j);

                int startTime = getRandStartTime();

                events.add( new Event(lecture, 300, random.nextInt(5), startTime, startTime + 2) );

                startTime = getRandStartTime();

                events.add( new Event(consultation, 300, random.nextInt(5), startTime, startTime + 2) );
            }

            for (int j = 0; j < groups.size(); j++)//generate seminaries
            {
                String seminar = subjects.get(i) + "_seminar_" + groups.get(j);

                int startTime = getRandStartTime();

                events.add( new Event(seminar, 30, random.nextInt(5), startTime, startTime + 2) );
            }
        }

        int roomNumber = 1;

        for (int i = 0; i < events.size(); i++)
        {
            rooms.add( new Laboratory("" + roomNumber + i, 30, Laboratory.OS.WINDOWS ) );
            rooms.add( new LectureHall("" + roomNumber + (i + 1), 300, true));
            //more than enough rooms will always be generated since rooms act as colors
        }
    }

    private ArrayList<String> getSubjects(int subjectsNumber)
    {
        File subjectsFile = new File("src/SubjectsFile.txt");
        try
        {
            Scanner scanner = new Scanner(subjectsFile);

            ArrayList<String> subjects = new ArrayList<>();

            while (scanner.hasNext() )
            {
                String line = scanner.nextLine();
                subjects.add(line);

                if (subjects.size() >= subjectsNumber)
                    break;
                //System.out.println(line);
            }

            return  subjects;

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<String> getSeries(int seriesNumber)
    {
        ArrayList<String> series = new ArrayList<>();

        for (int i = 0; i < seriesNumber; i++)
        {
            int letter = i % 26 + 65;//65 is the ascii for A
            int temp = (i / 26) + 1;

            String singleSeries = "";

            for (int k = 0; k < temp; k++)
            {
                singleSeries += (char) letter;
            }

            series.add(singleSeries);
        }

        return  series;
    }

    private ArrayList<String> getGroups(ArrayList<String> series, int groupNumber)
    {
        ArrayList<String> groups = new ArrayList<>();

        for (int i = 0; i < series.size(); i++)
        {
            for (int j = 0; j < groupNumber; j++)
            {
                String tempGroup = series.get(i) + (j + 1);
                groups.add(tempGroup);
            }
        }

        return  groups;
    }

    private int getRandStartTime()
    {
        Random random = new Random();
        int startTime = random.nextInt(10) + 8;

        if (startTime % 2 != 0)
            startTime--;
        //events can start between 8 and 18
        //only at even hours (8, 10, 12..)

        return startTime;
    }

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
