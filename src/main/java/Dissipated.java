package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class Dissipated implements FloodState {

    public static final int FLOOD_END_TIME = 20;

    @Override
    public void casualty(Event event, ResponderComm responder) {
        System.out.println("Flood dissipated. No more people hospitalised.");
    }

    @Override
    public void damage(Event event, ResponderComm responder) {
        System.out.println("Flood dissipated. No more property damaged."); 
    }

    @Override
    public void updateTime(FloodContext floodContext, int time, Event event, ResponderComm responder, List<String> fromPoll) {
        int eventStart = event.getTime();
        int dissipatedTime = eventStart + FLOOD_END_TIME;

        if(time == dissipatedTime)
        {
            floodContext.notifyObservers();
            casualty(event, responder);
            damage(event, responder);
        }
    }

    @Override
    public String toString() {
        return "dissipated";
    }
}
