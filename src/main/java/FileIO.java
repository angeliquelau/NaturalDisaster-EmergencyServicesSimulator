package edu.curtin.emergencysim;
import java.io.*;
import java.util.List;

//class for file io which in this case its reading input file
public class FileIO {
    public EventList readFile(String filename, List<Event> events/*, List<ResObserver> resObs, ResponderComm responder, String msg*/) throws AssTwoException
    {
        //sort the events here instead of in EventList
        EventList eventList = new EventList(events/*, resObs, responder, msg*/);
        String line;
        
        try
        {
            FileInputStream fs = new FileInputStream(filename);
            InputStreamReader read = new InputStreamReader(fs);
            BufferedReader bufRead = new BufferedReader(read);
            line = bufRead.readLine(); //reads the first line of the file
            
            while(line != null)
            {
                String[] splitLine = line.split(" ");

                Event event;
                //index 0 is time, index 1 is type and index 2 and 3 is location
                if(splitLine.length > 3) //if location name is more than one word, it enters this part
                {
                    event = new Event(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2] + " " + splitLine[3]);
                    
                    // System.out.println("if: " + eventMap.getEvents().toString());
                }
                else //if location name is one word, it enters here
                {
                    event = new Event(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2]);
                    // System.out.println("else: " + eventMap.getEvents().toString());
                }
                //save data into map (for easy use later in the program)
                eventList.addEvent(event);

                line = bufRead.readLine();
            }

            //close InputStreamReader and FileInputStream here
            read.close();
            fs.close();
        }
        catch(IOException e)
        {
            
            throw new AssTwoException("File not found. ", e);
        }
        
        return eventList;
    }
}
