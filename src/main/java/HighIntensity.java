package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class HighIntensity implements FireState {

    //time in seconds
    public static final int FIRE_LOW_TO_HIGH_TIME = 15; 
    //probability
    public static final double FIRE_HIGH_CASUALTY_PROB = 0.45; //45%
    public static final double FIRE_HIGH_DAMAGE_PROB = 0.60; //60%

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
        //if randomNum generated a number less than 0.45, then enter this if statement
        if(randomNum <= FIRE_HIGH_CASUALTY_PROB)
        {
            casualtyNum += 1; //increase the number of people hospitalised
            responder.send("fire casualty " + casualtyNum + " " + eventPlace);
        }
    }

    @Override
    public void damage(Event event, ResponderComm responder) {
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        //if randomNum generated a number less than 0.60, then enter this if statement
        if(randomNum <= FIRE_HIGH_DAMAGE_PROB)
        {
            damageNum += 1; //increase the number of property damaged
            responder.send("fire damage " + damageNum + " " + eventPlace);
        }
    }

    @Override
    public void updateTime(FireContext fireContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll) {
        String eventPlace = event.getLocation();
        int eventStart = event.getTime();
        int changeStateTime = eventStart + FIRE_LOW_TO_HIGH_TIME; //the time when the state changes

        if(time == changeStateTime)
        {         
            fireContext.notifyObservers();   
        }

        if(fromPoll.contains("[fire + " + eventPlace + "]")) //if fire fighters arrived at the event
        {
            fireContext.setState(new HighIntensityAttended());
        }
        else
        {
            casualty(event, responder);
            damage(event, responder);
        }
    }
    
    @Override
    public String toString() {
        return "high";
    }
}
