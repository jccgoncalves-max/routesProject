package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;
import dataStructures.*;
/**
 * Represents a Journey taken over the train line.
 */
public class JourneyClass implements Journey, Serializable {
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
    private List<Stops> stopsSequence;
    /**
     * Journey ticket
     */
    private transient Ticket journeyTicket;

    /**
     * JourneyClass constructor
     * @param id Journey's unique id
     */
    protected JourneyClass(String id){
        this.id = id;
        stopsSequence = new ArrayClass<>();
    }

    @Override
    public void addStop(Stops newStop) {
        stopsSequence.addLast(newStop);
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
    public Stops getStop(String stationName) {

        Iterator<Stops> it = stopsSequence.iterator();
        while (it.hasNext()) {
            Stops stops = it.next();
            if (stops.getStation().getName().equalsIgnoreCase(stationName))
                return stops;
        }
        return null;
    }
    @Override
    public List<Stops> getStops(){
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
