package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;
import java.util.List;

public class ChemContext {
    private ChemState chemical;
    private List<ResObserver> resObs; //observer
    private ResponderComm responder;
    private Event event;

    public void setState(ChemState inChemical)
    {
        chemical = inChemical;
    }

    public ChemState getState()
    {
        return chemical;
    }

    public void casualty(Event event, ResponderComm responder)
    {
        chemical.casualty(event, responder);
    }

    public void contamination(Event event, ResponderComm responder)
    {
        chemical.contamination(event, responder);
    }

    public void updateTime(int time, int pollTime, Event event, ResponderComm responder, List<String> fromPoll)
    {
        chemical.updateTime(this, time, pollTime, event, responder, fromPoll);
    }

    //for observer pattern
    public void chemObsInit(List<ResObserver> inResObs, ResponderComm inResponder, Event inEvent)
    {
        resObs = inResObs;
        responder = inResponder;
        event = inEvent;
    }

    public void notifyObservers()
    {
        for(ResObserver obs : resObs)
        {
            obs.updateChem(responder, this, event);
        }
    }

    //add new observer to observer's list
    public void addObserver(ResObserver obs)
    {
        resObs.add(obs);
    }
}
