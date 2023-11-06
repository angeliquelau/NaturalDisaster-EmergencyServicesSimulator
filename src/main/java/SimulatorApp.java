package edu.curtin.emergencysim;
import edu.curtin.emergencysim.responders.*;

import java.util.ArrayList;
import java.util.List;

/*
*
* to get command line arguments at program startup, do this:
* ./gradlew run --args='input.txt' (for example)
*
*/

public class SimulatorApp extends Thread
{
    public static void main(String[] args) throws AssTwoException
    {
        try {
            FileIO file = new FileIO();
            ErrorCheck error = new ErrorCheck();
            ResponderComm responder = new ResponderCommImpl();
            List<Event> events = new ArrayList<>(); 
            List<ResObserver> resObs = new ArrayList<>();
            ResObserver resObserver = new ResConcreteObs();
            FireContext fire = new FireContext();
            FloodContext flood = new FloodContext();
            ChemContext chemical = new ChemContext();

            String filename = args[0];
            error.checkFilename(filename);
            
            EventList eventList = file.readFile(filename, events);
            resObs.add(resObserver);
            
            /*check if there is any duplicates. if there are duplicates, throw exception and 
            message saying got duplicate*/
            error.checkDuplicate(events);

            int countSec = 1, i = 0, pollTime = 0;
            String fromPoll = "", location = "";
            List<String> eventPoll = new ArrayList<>();
            Event sortedEvent;

            //keep looping until responder sends end message
            while(!fromPoll.equals("[end]"))
            {
                fromPoll = responder.poll().toString();
                if(fromPoll.contains("+"))
                {
                    pollTime = countSec;
                }
                System.out.println("\ntime: " + countSec);
                System.out.println("responder: " + fromPoll);
                
                if(i < eventList.getSortedEvents().size())
                {
                    sortedEvent = eventList.getSortedEvents().get(i);
                    
                    /*if countSec is at the second an event happens, it enters this if statement.
                    otherwise, it will continue counting the second.*/
                   
                    if(countSec >= sortedEvent.getTime())
                    {
                        switch(eventList.getSortedEvents().get(i).getType())
                        {
                            case "fire":
                                // System.out.println("fireeeee");
                                fire.fireObsInit(resObs, responder, sortedEvent);
                                location = eventList.getSortedEvents().get(i).getLocation();
                                
                                if(fromPoll.contains("+") || fromPoll.contains("-"))
                                {
                                    error.checkREvent("fire", location, fromPoll);
                                    eventPoll.add(fromPoll);
                                }
    
                                //only enter this part if its at the second where the event happens
                                if(countSec == sortedEvent.getTime())
                                {
                                    fire.setState(new LowIntensity());
                                    fire.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                    
                                }
                                //if fire is still happening and not extinguished, keep entering here
                                else
                                {
                                    if(countSec > sortedEvent.getTime())
                                    {
                                        fire.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                    }
    
                                    if(fire.getState().toString().equals("extinguish"))
                                    {
                                        fire.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                        
                                        i++;
                                    }
                                }
                                break;
    
                            case "flood":
                                // System.out.println("flooooodddd");
                                flood.floodObsInit(resObs, responder, sortedEvent);
                                location = eventList.getSortedEvents().get(i).getLocation();

                                if(fromPoll.contains("+") || fromPoll.contains("-"))
                                {
                                    error.checkREvent("flood", location, fromPoll);
                                    eventPoll.add(fromPoll);
                                }
                                //only enter this part if its at the second where the event happens
                                if(countSec == sortedEvent.getTime())
                                {
                                    flood.setState(new Flooded());
                                    flood.updateTime(countSec, sortedEvent, responder, eventPoll);
                                }
                                //if fire is still happening and not extinguished, keep entering here
                                else 
                                {
                                    if(countSec > sortedEvent.getTime())
                                    {
                                        flood.updateTime(countSec, sortedEvent, responder, eventPoll);
                                    }
                                    
                                    if(flood.getState().toString().equals("dissipated"))
                                    {
                                        flood.updateTime(countSec, sortedEvent, responder, eventPoll);
                                       
                                        i++;
                                    }
                                }
                                break;
    
                            case "chemical":
                                // System.out.println("chemical");
                                chemical.chemObsInit(resObs, responder, sortedEvent);
                                location = eventList.getSortedEvents().get(i).getLocation();
                                if(fromPoll.contains("+") || fromPoll.contains("-"))
                                {
                                    error.checkREvent("chemical", location, fromPoll);
                                    eventPoll.add(fromPoll);
                                }
                                //only enter this part if its at the second where the event happens
                                if(countSec == sortedEvent.getTime())
                                {
                                    chemical.setState(new ChemSpilled());
                                    chemical.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                }
                                //if fire is still happening and not extinguished, keep entering here
                                else 
                                {
                                    if(countSec > sortedEvent.getTime())
                                    {
                                        chemical.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                    }
    
                                    if(chemical.getState().toString().equals("cleaned up"))
                                    {
                                        chemical.updateTime(countSec, pollTime, sortedEvent, responder, eventPoll);
                                     
                                        i++;
                                    }
                                }
                                break;
    
                            default:
                                error.checkREvent("others", location, fromPoll);
                                System.out.println("event invalid");  
                                break;
                        }
                    }
                    Thread.sleep(1000); //sleep for 1 second
                    countSec += 1;
                }
                else
                {
                    Thread.sleep(1000); //sleep for 1 second
                    countSec += 1;                
                }
            }

        } 
        catch (InterruptedException e)
        {
            throw new AssTwoException("Interrupted exception: ", e);
        }
        catch (AssTwoException e) 
        {
            throw new AssTwoException("Other exceptions: ", e);
        }
    }

     
    
}
