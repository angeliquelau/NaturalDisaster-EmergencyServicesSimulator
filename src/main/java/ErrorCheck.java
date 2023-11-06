package edu.curtin.emergencysim;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ErrorCheck {
    private static Logger logger = Logger.getLogger(ErrorCheck.class.getName()); //for error logging
    //check for event duplicates
    public void checkDuplicate(List<Event> events) throws AssTwoException
    {
        String type, location;
        for(int i = 0; i < events.size(); i++)
        {
            type = events.get(i).getType();
            location = events.get(i).getLocation();
            for(int j = i + 1; j < events.size(); j++)
            {
                //if got same event happening, then throw error message
                if(events.get(j).getType().equals(type) && events.get(j).getLocation().equals(location))
                {
                    throw new AssTwoException("Invalid input data. Got duplicate.");
                }
            }
        }
    }

    public void checkFilename(String filename) throws AssTwoException
    {
        //if filename contains numbers only
        if(filename.matches("[0-9]+"))
        {
            throw new AssTwoException("Please enter a valid filename.");
        }
        else if(!filename.contains(".txt")) //if file is not a .txt file
        {
            throw new AssTwoException("Please enter a .txt file.");
        }
    }

    public void checkREvent(String type, String location, String fromPoll)
    {  
        //if fromPoll don't contain the type or location of the event
        if(!fromPoll.contains(type) || !fromPoll.contains(location))
        {
            logger.log(Level.WARNING, "There is no such event.");
        }
    }
}
