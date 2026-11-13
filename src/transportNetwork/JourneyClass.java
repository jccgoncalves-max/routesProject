package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;
import dataStructures.*;
/**
 * Represents a Journey taken over the train line.
 * Space Complexity:
 * O(n) - Stores a sequence of n Stops in an underlying dynamic array (ArrayClass)
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
     * Time Complexity:
     * Best Case: O(1) - Assigns the journey id and allocates the initial fixed-capacity ArrayClass in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * JourneyClass constructor.
     *
     * @param id Journey's unique id
     */
    protected JourneyClass(String id){
        this.id = id;
        stopsSequence = new ArrayClass<>();
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Underlying array is not full, appending the stop directly at the end in constant time.
     * Worst Case: O(n) - Underlying array is full, requiring a resize that copies all n existing stops into a new array before appending.
     * Adds a new stop to the end of the journey's stop sequence.
     *
     * @param newStop stop to be added to the journey
     */
    @Override
    public void addStop(Stops newStop) {
        stopsSequence.addLast(newStop);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the unique identifier of the journey.
     *
     * @return journey's unique id
     */
    @Override
    public String getId() {
        return this.id;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the ticket associated with this journey.
     *
     * @return journey ticket, or null if not set
     */
    @Override
    public Ticket getJourneyTicket(){
        return journeyTicket;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Instantiates a new Ticket object and assigns the reference in constant time without loops.
     * Worst Case: O(1) - Constant time object instantiation and field assignment.
     * Creates and sets the ticket for this journey.
     *
     * @param journey journey associated with the ticket
     * @param departureStation station where the passenger departs
     * @param departureStationTime scheduled departure time
     * @param arrivalStation station where the passenger arrives
     * @param arrivalStationTime scheduled arrival time
     * @param stopCount number of stops between departure and arrival
     */
    @Override
    public void setJourneyTicket(Journey journey, Station departureStation, LocalTime departureStationTime, Station arrivalStation, LocalTime arrivalStationTime, int stopCount){
        journeyTicket = new Ticket(journey,departureStation,departureStationTime,arrivalStation,arrivalStationTime,stopCount);
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Finds the matching station at the first stop in the sequence on the first iteration.
     * Worst Case: O(n) - Iterates through all n stops in the sequence when the station is at the last stop or does not exist.
     * Searches for and returns the stop corresponding to the specified station name.
     *
     * @param stationName name of the station to search for
     * @return matching Stops object, or null if not found
     */
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

    /**
     * Time Complexity:
     * Best Case: O(1) - Returns the reference to stopsSequence directly in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the sequence of stops of this journey.
     *
     * @return list of stops in the journey
     */
    @Override
    public List<Stops> getStops(){
        return stopsSequence;
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - The loop unconditionally traverses all n stops in the sequence (accessing each in O(1)) to find both origin and destination indices.
     * Worst Case: O(n) - Traverses all n stops in the sequence via index-based access to determine the distance between stations.
     * Calculates the number of stops between the origin station and the destination station.
     *
     * @param originStation name of the departure station
     * @param destinationStation name of the arrival station
     * @return number of stops between origin and destination, or NOT_FOUND (-1) if invalid
     */
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
