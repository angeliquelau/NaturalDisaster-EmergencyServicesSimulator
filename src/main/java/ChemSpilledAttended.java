package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class ChemSpilledAttended implements ChemState {
    //time in seconds
    public static final int CHEM_CLEANUP_TIME = 30;
    //probability
    public static final double CHEM_CASUALTY_PROB = 0.20;
    public static final double CHEM_CONTAM_PROB = 0.75;

    //for random number generator
    private double min = 0.0;
    private double max = 1.0;
    private int casualtyNum = 0; //for keeping count of number of people hospitalised
    private int contamNum = 0; //for keeping count of number of property damaged

    @Override
    public void casualty(Event event, ResponderComm responder) {
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.20, then enter this if statement
        if(randomNum <= CHEM_CASUALTY_PROB)
        {
            casualtyNum += 1; //increase the number of people hospitalised
            responder.send("chemical casualty " + casualtyNum + " " + eventPlace);        
        }
        
    }

    @Override
    public void contamination(Event event, ResponderComm responder) {
        String eventPlace = event.getLocation();
        double randomNum = (Math.random()*(max - min)) + min;
        //round up to 2 decimal places
        randomNum = Math.round(randomNum * 100.0)/100.0;
        // System.out.println("randomNum: " + randomNum);
        //if randomNum generated a number less than 0.75, then enter this if statement
        if(randomNum <= CHEM_CONTAM_PROB)
        {
            contamNum += 1; //increase the number of the contamination
            responder.send("chemical contam " + contamNum + " " + eventPlace);
        }
        
    }

    @Override
    public void updateTime(ChemContext chemContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll) {

        String eventPlace = event.getLocation();
        int eventEnd = pollTime + CHEM_CLEANUP_TIME;

        // System.out.println("hello from chem spill attended");
        
        // if(fromPoll.contains("[chemical - " + eventPlace + "]") && time < eventEnd)
        // {
        //     casualty(event, responder);
        //     contamination(event, responder);
        // }
        //if rescuers leave when the chemical is cleaned up, then change state to cleaned up
        if(fromPoll.contains("[chemical - " + eventPlace + "]") && time >= eventEnd)
        {
            chemContext.setState(new CleanedUp());
        }
        else //if rescuers leave before the chemical is cleaned up, continue the count of casualty and contamination
        {
            casualty(event, responder);
            contamination(event, responder);
        }
    }

    @Override
    public String toString() {
        return "chemical spilled attended";
    }
}
