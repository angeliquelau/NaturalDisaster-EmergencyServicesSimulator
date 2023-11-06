package edu.curtin.emergencysim;

public class Event {
    private int time;
    private String type;
    private String location;

    public Event()
    {
        time = 0;
        type = "";
        location = "";
    }

    public Event(int inTime, String inType, String inLocation)
    {
        time = inTime;
        type = inType;
        location = inLocation;
    }

    //getters
    public int getTime()
    {
        return time;
    }

    public String getType()
    {
        return type;
    }

    public String getLocation()
    {
        return location;
    }

    //setters
    public void setTime(int inTime)
    {
        time = inTime;
    }

    public void setType(String inType)
    {
        type = inType;
    }

    public void setLocation(String inLocation)
    {
        location = inLocation;
    }

    @Override
    public String toString() {
        return "time: " + getTime() + ", type: " + getType() + ", location: " + getLocation();
    }
}
