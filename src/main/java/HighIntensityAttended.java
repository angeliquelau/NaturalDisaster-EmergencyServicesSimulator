package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class HighIntensityAttended implements FireState {
    //time in seconds
    public static final int FIRE_HIGH_TO_LOW_TIME = 15;
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
        // System.out.println("we in highhh ");
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.05, then enter this if statement
        if(randomNum <= FIRE_HIGH_CASUALTY_PROB)
        {
            casualtyNum += 1; //increase the number of people hospitalised
            responder.send("fire casualty " + casualtyNum + " " + eventPlace);
        }
    }

    @Override
    public void damage(Event event, ResponderComm responder) {
        // System.out.println("we in highhhh attended ");
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.05, then enter this if statement
        if(randomNum <= FIRE_HIGH_DAMAGE_PROB)
        {
            damageNum += 1; //increase the number of property damaged
            responder.send("fire damage " + damageNum + " " + eventPlace);
        }
    }

    @Override
    public void updateTime(FireContext fireContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll) {
        String eventPlace = event.getLocation();
        int changeStateTime = pollTime + FIRE_HIGH_TO_LOW_TIME; //from the time responder come to try reduce the intensity of the fire

        if(time == changeStateTime && fromPoll.contains("[fire + " + eventPlace + "]"))
        {
            fireContext.setState(new LowIntensityAttended());
        }
        else if(time < changeStateTime && fromPoll.contains("[fire - " + eventPlace + "]")) //if fire fighter leave before the decrease of fire intensity 
        {
            fireContext.setState(new HighIntensity());
        }
        else //if its not time to change state yet then keep getting number of casualty and property damaged
        {
            casualty(event, responder);
            damage(event, responder);
        }
    }

    @Override
    public String toString() {
        return "high attended";
    }
}
