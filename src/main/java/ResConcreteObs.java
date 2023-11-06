package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;

public class ResConcreteObs implements ResObserver {

    //for fire state
    @Override
    public void updateFire(ResponderComm responder, FireContext fire, Event event) {
        String eventPlace = event.getLocation();
        if(fire.getState().toString().equals("low"))
        {
            responder.send("fire start " + eventPlace);
            responder.send("fire low " + eventPlace);
        }
        else if(fire.getState().toString().equals("high"))
        {
            responder.send("fire high " + eventPlace);
        }
        else if(fire.getState().toString().equals("extinguish"))
        {
            responder.send("fire end " + eventPlace);
        }
        
    }

    //for flood state
    @Override
    public void updateFlood(ResponderComm responder, FloodContext flood, Event event) {
        String eventPlace = event.getLocation();
        if(flood.getState().toString().equals("flooded"))
        {
            responder.send("flood start " + eventPlace);
        }
        else if(flood.getState().toString().equals("dissipated"))
        {
            responder.send("flood end " + eventPlace);
        }
        
    }

    //for chemical spill state
    @Override
    public void updateChem(ResponderComm responder, ChemContext chem, Event event) {
        String eventPlace = event.getLocation();
        if(chem.getState().toString().equals("chemical spilled"))
        {
            responder.send("chemical start " + eventPlace);
        }
        else if(chem.getState().toString().equals("cleaned up"))
        {
            responder.send("chemical end " + eventPlace);
        }
    }
    
}
