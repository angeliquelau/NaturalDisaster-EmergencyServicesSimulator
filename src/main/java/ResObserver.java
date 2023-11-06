package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;

public interface ResObserver {
    public void updateFire(ResponderComm responder, FireContext fire, Event event);
    public void updateFlood(ResponderComm responder, FloodContext flood, Event event);
    public void updateChem(ResponderComm responder, ChemContext chem, Event event);
}
