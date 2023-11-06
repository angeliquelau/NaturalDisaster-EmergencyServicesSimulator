package edu.curtin.emergencysim;
import java.util.List;

//subject for observer pattern
public class EventList 
{
    private List<Event> events; 

    public EventList(List<Event> newEvents)
    {
        events = newEvents;
    }

    //add events into array list
    public void addEvent(Event event)
    {
        events.add(event);
    }

    public List<Event> getSortedEvents()
    {
        //i created this because of the way i sort my events
        Event temp = new Event();
        for(int i = 0; i < events.size(); i++)
        {
            for(int j = i + 1; j < events.size(); j++)
            {
                //sort the events 
                if(events.get(i).getTime() > events.get(j).getTime())
                {
                    //temporary storing the data at index i in 'temp'
                    temp.setTime(events.get(i).getTime()); 
                    temp.setType(events.get(i).getType()); 
                    temp.setLocation(events.get(i).getLocation()); 

                    //storing the data from index 'j' to the index 'i'
                    events.get(i).setTime(events.get(j).getTime()); 
                    events.get(i).setType(events.get(j).getType()); 
                    events.get(i).setLocation(events.get(j).getLocation()); 

                    //storing the data stored in temp to index 'j' to temp
                    events.get(j).setTime(temp.getTime()); 
                    events.get(j).setType(temp.getType()); 
                    events.get(j).setLocation(temp.getLocation()); 
                }
            }
        }
        return events;
    }
}
