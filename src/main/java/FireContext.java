package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class FireContext {
    private FireState fire;
    private List<ResObserver> resObs; //observer
    private ResponderComm responder;
    private Event event;

    public void setState(FireState inFire)
    {
        fire = inFire;
    }

    public FireState getState()
    {
        return fire;
    }

    public void casualty(Event event, ResponderComm responder)
    {
        fire.casualty(event, responder);
    }

    public void damage(Event event, ResponderComm responder)
    {
        fire.damage(event, responder);
    }

    public void updateTime(int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll)
    {
        fire.updateTime(this, time, pollTime, event, responder, fromPoll);
    }

    //for observer pattern
    public void fireObsInit(List<ResObserver> inResObs, ResponderComm inResponder, Event inEvent)
    {
        resObs = inResObs;
        responder = inResponder;
        event = inEvent;
    }

    public void notifyObservers()
    {
        for(ResObserver obs : resObs)
        {
            obs.updateFire(responder, this, event);
        }
    }

    //add new observer to observer's list
    public void addObserver(ResObserver obs)
    {
        resObs.add(obs);
    }
}
