package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class Extinguish implements FireState {

    public static final int FIRE_LOW_CLEANP_TIME = 8;

    @Override
    public void casualty(Event event, ResponderComm responder) {
        System.out.println("Fire extinguished. No more people hospitalised.");
    }

    @Override
    public void damage(Event event, ResponderComm responder) {
        System.out.println("Fire extinguished. No more property damaged.");
    }

    @Override
    public void updateTime(FireContext fireContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll) {
        int extinguishTime = pollTime + FIRE_LOW_CLEANP_TIME;

        // '>=' in case the fire fighters leave late for the fire is extinguished
        if(time >= extinguishTime)
        {
            fireContext.notifyObservers();
            casualty(event, responder);
            damage(event, responder);
        }
        
    }

    @Override
    public String toString() {
        return "extinguish";
    }
}
