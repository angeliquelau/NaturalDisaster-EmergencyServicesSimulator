package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class FloodAttended implements FloodState {
    //time in seconds
    public static final int FLOOD_END_TIME = 20;
    //probability
    public static final double FLOOD_CASUALTY_PROB = 0.10;
    public static final double FLOOD_DAMAGE_PROB = 0.30;

    //for random number generator
    private double min = 0.0;
    private double max = 1.0;
    private int casualtyNum = 0; //for keeping count of number of people hospitalised
    private int damageNum = 0; //for keeping count of number of property damaged

    @Override
    public void casualty(Event event, ResponderComm responder) {    
        System.out.println("Flood rescuers arrived. No more people hospitalised.");        
    }

    //method is called when responders leave before flood dissipated
    private void casualtyResLeft(Event event, ResponderComm responder)
    {
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.10, then enter this if statement
        if(randomNum <= FLOOD_CASUALTY_PROB)
        {
            casualtyNum += 1; //increase the number of people hospitalised
            // System.out.println("[flood] " + casualtyNum + " person(s) hospitalised.");
            responder.send("flood casualty " + casualtyNum + " " + eventPlace);
        }
    }

    @Override
    public void damage(Event event, ResponderComm responder) {
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.30, then enter this if statement
        if(randomNum <= FLOOD_DAMAGE_PROB)
        {
            damageNum += 1; //increase the number of property damaged
            responder.send("flood damage " + damageNum + " " + eventPlace);
        }
    }

    @Override
    public void updateTime(FloodContext floodContext, int time, Event event, ResponderComm responder, List<String> fromPoll) {
        String eventPlace = event.getLocation();
        int eventStart = event.getTime();
        int dissipateTime = eventStart + FLOOD_END_TIME;
        damage(event, responder);
        //flood havent dissipate but flood rescuers already leave
        //time is the current second the program is on and dissipateTime is the time calculated for the flood to end
        //so if the current second have not reach dissipateTime yet then it will keep updating the number of casualties
        if(fromPoll.contains("[flood - " + eventPlace + "]") && time != dissipateTime) 
        {
            casualtyResLeft(event, responder);
        }
        else if(time == dissipateTime) //dissipate time is reached
        {
            floodContext.setState(new Dissipated());
        }
        else //flood havent dissipate but the flood rescuers are still around
        {
            casualty(event, responder);
        }
        
    }

    @Override
    public String toString() {
        return "flooded";
    }
}
