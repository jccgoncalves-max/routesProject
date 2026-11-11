package transportNetwork;

import java.time.LocalTime;
import java.util.*;

/**
 * Represents a Journey taken over the train line.
 */
public class JourneyClass implements Journey{
    /**
     * Journey's unique id.
     */
    private String id;
    /**
     * Journey's sequence of stops.
     */
    private ArrayList<Stops> stopsSequence;
    /**
     * Journey ticket
     */
    private Ticket journeyTicket;

    /**
     * JourneyClass constructor
     * @param id Journey's unique id
     */
    public JourneyClass(String id){
        this.id = id;
        stopsSequence = new ArrayList<>();
    }

    @Override
    public void addStop(Stops newStop) {
        stopsSequence.add(newStop);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Ticket getJourneyTicket(){
        return journeyTicket;
    }

   /*
    @Override
    public Stops getDepartureStation(){
        return stopsSequence.getFirst();
    }


    @Override
    public Stops getArrivalStation(){
        return stopsSequence.getLast();
    }
    */

    @Override
    public void setJourneyTicket(Journey journey, Station departure, LocalTime departureTime, Station arrival, LocalTime arrivalTime){
        journeyTicket = new Ticket(journey,departure,departureTime,arrival,arrivalTime);
    }

    @Override
    public Stops getStop(String stationName){
            for(Stops stop : stopsSequence){
                if(stop.getStation().getName().equalsIgnoreCase(stationName))
                    return stop;
            }
            return null;
        }
}
