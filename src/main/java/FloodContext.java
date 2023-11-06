package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class FloodContext {
    private FloodState flood;
    private List<ResObserver> resObs; //observer
    private ResponderComm responder;
    private Event event;

    public void setState(FloodState inFlood)
    {
        flood = inFlood;
    }

    public FloodState getState()
    {
        return flood;
    }

    public void casualty(Event event, ResponderComm responder)
    {
        flood.casualty(event, responder);
    }

    public void damage(Event event, ResponderComm responder)
    {
        flood.damage(event, responder);
    }

    public void updateTime(int time, Event event, ResponderComm responder, List<String> fromPoll)
    {
        flood.updateTime(this, time, event, responder, fromPoll);
    }

    //for observer pattern
    public void floodObsInit(List<ResObserver> inResObs, ResponderComm inResponder, Event inEvent)
    {
        resObs = inResObs;
        responder = inResponder;
        event = inEvent;
    }

    public void notifyObservers()
    {
        for(ResObserver obs : resObs)
        {
            obs.updateFlood(responder, this, event);
        }
    }

    //add new observer to observer's list
    public void addObserver(ResObserver obs)
    {
        resObs.add(obs);
    }
}

