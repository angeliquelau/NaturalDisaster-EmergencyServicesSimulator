package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class CleanedUp implements ChemState {

    public static final int CHEM_CLEANUP_TIME = 30;

    @Override
    public void casualty(Event event, ResponderComm responder) {
        System.out.println("Chemical is cleaned up. No more people hospitalised.");
    }

    @Override
    public void contamination(Event event, ResponderComm responder) {
        System.out.println("Chemical is cleaned up. No more property damaged.");
    }

    @Override
    public void updateTime(ChemContext chemContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll) {

        int eventEnd = pollTime + CHEM_CLEANUP_TIME;

        if(time >= eventEnd)
        {
            chemContext.notifyObservers();
            casualty(event, responder);
            contamination(event, responder);
        }
    }
    
    @Override
    public String toString() {
        return "cleaned up";
    }
}
