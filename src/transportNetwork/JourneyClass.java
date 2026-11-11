package transportNetwork;

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

    /**
     * Gets Departure stop
     * @return Departure station
     */
    private Stops getDepartureStation(){
        return stopsSequence.getFirst();
    }

    /**
     * Gets arrival Stop
     * @return Departure stop
     */
    private Stops getArrivalStation(){
        return stopsSequence.getLast();
    }

    /**
     * Sets Journey ticket
     */
    private void setJourneyTicket(){
        journeyTicket = new Ticket(this,getDepartureStation().getStation(),getDepartureStation().getDepartureTime(),
                getArrivalStation().getStation(),getArrivalStation().getArrivalTime());
    }
}
