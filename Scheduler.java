package com.bercoviciadrianpa2022lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class that creates a viable solution of assigning rooms to events
 * Throughout the class the terms
 * -rooms and colors
 * -events and nodes
 * Will be used interchangeably for algorithmic purposes
 */

public class Scheduler
{
    static final int NANO_PER_SEC = 1;

    class EventAdjacencyList
    {
        ArrayList<Integer> adjacentEventIndexes = new ArrayList<Integer>();

        public void addAdjacency(int index)
        {
            adjacentEventIndexes.add(index);
        }

        public void debugPrintAdjacency(int index)
        {
            System.out.print("Adjacency list of node " + index + ":   ");

            for (int i = 0; i < adjacentEventIndexes.size(); i++)
            {
                System.out.print( adjacentEventIndexes.get(i) + " " );
            }

            System.out.print("\n");
        }
    }

    class UsedRoomCounter
    {
        private ArrayList<Boolean> roomIsUsed = new ArrayList<Boolean>();

        UsedRoomCounter(int maxRooms)
        {
            for (int i = 0; i < maxRooms; i++)
            {
                roomIsUsed.add(false);
            }
        }

        public void useRoom(int roomIndex)
        {
            roomIsUsed.set(roomIndex, true);
        }

        public int getUsedRooms()
        {
            int total = 0;

            for (int i = 0; i < roomIsUsed.size(); i++)
            {
                if (roomIsUsed.get(i))
                    total++;
            }

            return total;
        }
    }

    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<EventAdjacencyList> eventAdjacencyLists = new ArrayList<EventAdjacencyList>();
    private int roomsUsed = 0;
    private float executionDuration = 0;

    Scheduler ( ArrayList<Room> rooms, ArrayList<Event>  events)
    {
        //by design,the lists of rooms and events cannot be modified outside the constructor
        //as that could compromise the adjacency lists

        for (int i = 0; i < rooms.size(); i++)
        {
            tryAddRoom( rooms.get(i) );
        }

        for (int i = 0; i < events.size(); i++)
        {
            tryAddEvent(events.get(i));
        }

        constructAdjacencyLists();

        //sort rooms by capacity ascendingly
        //that way an event will always be assigned the smallest possible room that fits them (the smallest first)
        sortRoomsByCapacity();

    }

    private void tryAddRoom( Room room )
    {
        //duplicate rooms cannot exist
        for (int i = 0; i < rooms.size(); i++)
        {
            if (rooms.get(i).equals(room))
                return;
        }

        rooms.add(room);
    }

    private  void tryAddEvent( Event event )
    {
        //duplicate events cannot exist
        for (int i = 0; i < events.size(); i++)
        {
            if (events.get(i).equals(event))
                return;
        }

        events.add(event);
    }

    private void constructAdjacencyLists()
    {
        for (int i = 0; i < events.size(); i++)
        {
            eventAdjacencyLists.add( new EventAdjacencyList() );

            for (int j = 0; j < events.size(); j++)
            {
                if ( i == j)
                    continue;

                if ( Event.eventsOverlap( events.get(i), events.get(j) ) )
                    eventAdjacencyLists.get(i).addAdjacency(j);
            }
        }
    }

    private void sortRoomsByCapacity()
    {
        for (int i = 0; i < rooms.size() - 1; i ++)
        {
            for (int j = i + 1; j < rooms.size(); j++)
            {
                if ( rooms.get(i).getCapacity() > rooms.get(j).getCapacity() )
                {
                    Room aux = rooms.get(i);
                    rooms.set(i, rooms.get(j));
                    rooms.set(j, aux);
                }
            }
        }
    }

    private void resetRoomAssignations()
    {
        //this resets the color of the nodes, allowing for a recoloring
        for (int i = 0; i < events.size(); i++)
        {
            events.get(i).setAssociatedRoom(null);
        }
    }

    private int colorNode(int i)//returns the index of the room(color) used
    {
        LinkedList<Room> adjacentColors = new LinkedList<Room>();

        for (int k = 0; k < eventAdjacencyLists.get(i).adjacentEventIndexes.size(); k++)
        {
            int neighborIndex = eventAdjacencyLists.get(i).adjacentEventIndexes.get(k);
            adjacentColors.add( events.get(neighborIndex).getAssociatedRoom() );
        }

        //at this point, adjacentColors contains all the colors of the adjacent rooms
        //The rooms must be sorted ascending-ly by capacity so that at any given point the smallest room possible is assigned
        //If rooms are not sorted, coloring will be INVALID!

        for (int j = 0; j < rooms.size(); j++)
        {
            Room possibleColor = rooms.get(j);

            boolean colorIsValid = true;

            if (possibleColor.getCapacity() < events.get(i).getParticipantsNumber())
            {
                colorIsValid = false;//room is not big enough
                continue;
            }

            for (int k = 0; k < adjacentColors.size(); k++)
            {
                if (possibleColor.equals( adjacentColors.get(k) )) {
                    colorIsValid = false;//two adjacent nodes cannot have the same color
                    break;
                }
            }

            if (colorIsValid) {
                //at this point room J is a valid color for event I
                events.get(i).setAssociatedRoom(possibleColor);
                return j;
                //usedRoomCounter.useRoom(j);
                //break;
            }
        }

        return -1;
    }

    private boolean greedyColoring()//returns true if coloring was successful
    {
        resetRoomAssignations();

        //greedy coloring requires an ordering of the nodes
        //the quality of the result depends heavily on this ordering
        //for this implementation, the already provided ordering will be used
        //for algorithmic reasons, as far as this method is concered the terms:
        //rooms and colors
        //events and nodes
        //will be used interchangeably;

        UsedRoomCounter usedRoomCounter = new UsedRoomCounter(rooms.size());

        for (int i = 0; i < events.size(); i++)
        {
            int chosenColor = colorNode(i);

            if (chosenColor != -1)
                usedRoomCounter.useRoom(chosenColor);
            else
            {
                System.out.println("Scheduling impossible! - Insufficient rooms!");
                return false;
            }
        }

        roomsUsed = usedRoomCounter.getUsedRooms();
        return true;

    }

    private int getHighestSaturationIndex()
    {
        int degreesOfSaturation[] = new int[events.size()];

        //set degrees of saturation

        for (int i = 0; i < degreesOfSaturation.length; i++)
        {
            if (events.get(i).getAssociatedRoom() != null)
                degreesOfSaturation[i] = -1;
            else
            {
                degreesOfSaturation[i] = 0;//probably not necessary, most likely it is already 0

                HashMap<String, String> adjacentColors = new HashMap<>();

                for (int j = 0; j < eventAdjacencyLists.get(i).adjacentEventIndexes.size(); j++)
                {

                    int neighborIndex = eventAdjacencyLists.get(i).adjacentEventIndexes.get(j);

                    if ( events.get(neighborIndex).getAssociatedRoom() == null )
                        continue;

                    if (!adjacentColors.containsKey(events.get(neighborIndex).getAssociatedRoom().getRoomName()))
                        adjacentColors.put(events.get(neighborIndex).getAssociatedRoom().getRoomName(), "-");
                }

                degreesOfSaturation[i] += adjacentColors.size();
            }
        }

        //find the highest degree of saturation

        int maxIndex = -1, maxDegree = 0;

        for (int i = 0; i < degreesOfSaturation.length; i++)
        {
            if (degreesOfSaturation[i] >= maxDegree)
            {
                maxDegree = degreesOfSaturation[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    //https://www.geeksforgeeks.org/dsatur-algorithm-for-graph-coloring/
    //https://en.wikipedia.org/wiki/DSatur
    private boolean degreeOfSaturationColoring()//returns true if coloring was successful
    {
        //the difference between DSATUR and greedy coloring
        //is how the ordering of the nodes is dynamically generated in DSATUR as opposed to static in greedy
        //DASTUR always prioritizes the node with the highest degree of saturation
        //the degree of saturation is the number of DIFFERENT colors currently assigned to the neighbors of a node
        //a degree of node that has already been asigned a color will be -1
        resetRoomAssignations();

        UsedRoomCounter usedRoomCounter = new UsedRoomCounter(rooms.size());

        int highestSaturationNode = getHighestSaturationIndex();

        while (highestSaturationNode != -1)
        {
            int chosenColor = colorNode(highestSaturationNode);

            if (chosenColor != -1)
                usedRoomCounter.useRoom(chosenColor);
            else
            {
                System.out.println("Scheduling impossible! - Insufficient rooms!");
                return false;
            }

            highestSaturationNode = getHighestSaturationIndex();
        }

        roomsUsed = usedRoomCounter.getUsedRooms();

        return true;
    }

    public void applyGraphColoring(int strategy)
    {
        long startTime = System.nanoTime();
        long endTime;
        boolean coloringIsValid;
        if (strategy == 1)
        {
            System.out.println("Greedy coloring: ");

            coloringIsValid = greedyColoring();

            endTime = System.nanoTime();

            executionDuration = (endTime - startTime) / NANO_PER_SEC;

            if (coloringIsValid)
                debugPrintSchedule();
        }
        else
        {
            System.out.println("DSATUR coloring: ");

            coloringIsValid = degreeOfSaturationColoring();

            endTime = System.nanoTime();

            executionDuration = (endTime - startTime) / NANO_PER_SEC;

            if (coloringIsValid)
                debugPrintSchedule();
        }
    }

    void debugPrintSchedule()
    {
        for (int i = 0; i < events.size() - 1; i++)
        {
            for (int j = i + 1; j < events.size(); j++)
            {
                if ( Event.compare(events.get(i), events.get(j)) > 0 )
                {
                    Event aux = events.get(i);
                    events.set(i, events.get(j));
                    events.set(j, aux);
                }
            }
        }//sort by day and hour

        for (int i = 0; i < events.size(); i++)
        {
            System.out.println( events.get(i).toString() + " at: " + events.get(i).getAssociatedRoom());
        }

        System.out.println("Rooms used: " + roomsUsed);
        System.out.println("Execution time: " + executionDuration + " nano sec");
    }

}
