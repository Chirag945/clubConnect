package clubConnect.dao;

import clubConnect.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    public EventDAO(){

    }

    public List<Event> getAllEvents(){
        //COMPLETE THIS BELOW CODE JUST FOR TESTING
        List<Event> events = new ArrayList<>();
        return events;
    }

    public List<Event> getEventsMatchingTags(List<String> interests){
        //COMPLETE THIS BELOW CODE JUST FOR TESTING
        List<Event> events = new ArrayList<>();
        return events;
    }

    public Event getEventById(int id){
        Event e=new Event();
        return e;
    }
}
