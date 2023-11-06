package edu.curtin.emergencysim;
import java.util.List;

import edu.curtin.emergencysim.responders.*;

public interface FireState {
    //method to find how many people are hospitalised
    public void casualty(Event event, ResponderComm responder); 
    //method to find the number of buildings destroyed
    public void damage(Event event, ResponderComm responder); 
    //keep track of the time
    public void updateTime(FireContext fireContext, int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll);
}
