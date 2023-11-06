package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class Flooded implements FloodState {

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
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.10, then enter this if statement
        if(randomNum <= FLOOD_CASUALTY_PROB)
        {
            casualtyNum += 1; //increase the number of people hospitalised
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

        if(time == eventStart) //at the start of the event, send responder information that the event started
        {
            // responder.send("flood start " + eventPlace);
            floodContext.notifyObservers();
        }
        else if(fromPoll.contains("[flood + " + eventPlace + "]")) //if the flood rescuers arrived
        {
            floodContext.setState(new FloodAttended());
        }
        else //if there is no flood rescuers, then wait until the flood's dissipated time
        {
            casualty(event, responder);
            damage(event, responder);
            if(time == dissipateTime)
            {
                floodContext.setState(new Dissipated());
            }
        }
        // casualty(/*floodContext*/event, responder);
        
    }

    @Override
    public String toString() {
        return "flooded";
    }
}
