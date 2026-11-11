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

    /**
     * Gets Departure stop
     * @return Departure station
     */
    @Override
    public Stops getDepartureStation(){
        return stopsSequence.getFirst();
    }

    /**
     * Gets arrival Stop
     * @return Departure stop
     */
    @Override
    public Stops getArrivalStation(){
        return stopsSequence.getLast();
    }

    /**
     * Sets Journey ticket
     */
    @Override
    public void setJourneyTicket(Journey journey, Station departure, LocalTime departureTime, Station arrival, LocalTime arrivalTime, int stopCount){
        journeyTicket = new Ticket(journey,departure,departureTime,arrival,arrivalTime,stopCount);
    }

    @Override
    public void setJourneyTicket(Journey journey, Station departure, LocalTime departureTime, Station arrival, LocalTime arrivalTime) {
        setJourneyTicket(journey, departure, departureTime, arrival, arrivalTime, 0);
    }

    @Override
    public Stops getStopAt(String stationName) {
        if (stationName == null) {
            return null;
        }
        for (Stops stop : stopsSequence) {
            if (stop.getStation().getName().equalsIgnoreCase(stationName)) {
                return stop;
            }
        }


        return null;
    }

    @Override
    public int getStopsCountBetween(String originStation, String destinationStation) {

        int originIndex = -1;
        int destIndex = -1;

        for (int i = 0; i < stopsSequence.size(); i++) {
            String stationName = stopsSequence.get(i).getStation().getName();

            if (stationName.equalsIgnoreCase(originStation)) {
                originIndex = i;
            }
            if (stationName.equalsIgnoreCase(destinationStation)) {
                destIndex = i;
            }
        }
        if (originIndex != -1 && destIndex != -1 && destIndex > originIndex){

            return destIndex - originIndex;
        }

        return  -1;
    }
}
