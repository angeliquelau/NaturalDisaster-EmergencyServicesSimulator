package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public interface FloodState {
    //method to find how many people are hospitalised
    public void casualty(Event event, ResponderComm responder); 
    //method to find the number of buildings destroyed
    public void damage(Event event, ResponderComm responder); 
    //keep track of the time
    public void updateTime(FloodContext floodContext, int time, Event event, ResponderComm responder, List<String> fromPoll);
}
