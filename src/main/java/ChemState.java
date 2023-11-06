package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public interface ChemState {
    //method to find how many people are hospitalised
    public void casualty(Event event, ResponderComm responder); 
    //method to find how bad the chemical spill will contaminate the environment
    public void contamination(Event event, ResponderComm responder); 
    //keep track of the time
    public void updateTime(ChemContext chemContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll);
}
