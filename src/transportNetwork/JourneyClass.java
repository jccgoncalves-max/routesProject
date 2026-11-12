package transportNetwork;

import java.time.LocalTime;
import java.util.*;

/**
 * Represents a Journey taken over the train line.
 */
public class JourneyClass implements Journey{
    private static final long serialVersionUID = 1L;
    /**
     * Not found constant
     */
    private final int NOT_FOUND = -1;
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
    protected JourneyClass(String id){
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


    @Override
    public void setJourneyTicket(Journey journey, Station departureStation, LocalTime departureStationTime, Station arrivalStation, LocalTime arrivalStationTime, int stopCount){
        journeyTicket = new Ticket(journey,departureStation,departureStationTime,arrivalStation,arrivalStationTime,stopCount);
    }


    @Override
    public Stops getStop(String stationName){
        for(Stops stop : stopsSequence){
            if(stop.getStation().getName().equalsIgnoreCase(stationName))
                return stop;
        }
        return null;
    }

    @Override
    public ArrayList<Stops> getStops(){
        return stopsSequence;
    }


    @Override
    public int getStopsCountBetween(String originStation, String destinationStation) {

        int originIndex = NOT_FOUND;
        int destinationIndex = NOT_FOUND;

        for (int i = 0; i < stopsSequence.size(); i++) {
            String stationName = stopsSequence.get(i).getStation().getName();

            if (stationName.equalsIgnoreCase(originStation)) {
                originIndex = i;
            }
            if (stationName.equalsIgnoreCase(destinationStation)) {
                destinationIndex = i;
            }
        }
        if (originIndex != NOT_FOUND && destinationIndex != NOT_FOUND && destinationIndex > originIndex){

            return destinationIndex - originIndex;
        }

        return  NOT_FOUND;
    }
}
