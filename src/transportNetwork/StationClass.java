package transportNetwork;
import java.io.Serializable;
import java.time.*;
import dataStructures.*;
/**
 * Represents a station on the train line, managing its scheduled departures
 * and suitable journey searches.
 * Space Complexity:
 * O(n) - Stores the station name and line position in O(1) space, plus a binary search tree
 * (BSTree) of singly linked lists indexing all n journeys departing from this station.
 */
public class StationClass implements Station , Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Station unique name
     */
    private String name ;
    /**
     * Station position on the line
     */
   private int linePosition;
    /**
     * Tree of stations ordered by hour
     */
    private SortedMap<LocalTime,List<Journey>>stationsOrderedByHour;

    /**
     * Time Complexity:
     * Best Case: O(1) - Assigns station attributes and initializes an empty BSTree in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * Station Constructor.
     *
     * @param name Station unique name
     * @param linePosition Station line position
     */
    protected StationClass(String name,int linePosition) {
        this.name = name;
        this.linePosition = linePosition;
        stationsOrderedByHour = new BSTree<>();
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the unique name of the station.
     *
     * @return station name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Root has no left child, its departure time is at or after passengerTime, and the first journey contains arrivalStation and this station at the start of its stops list.
     * Worst Case: O(n^2) - Traverses all n departures in the schedule and scans all n stops of each journey via getStop without finding an earlier match.
     * Finds the earliest journey departing at or after passengerTime that stops at arrivalStation.
     *
     * @param arrivalStation name of the destination station
     * @param passengerTime earliest acceptable departure time
     * @return ticket for the first suitable journey found, or null if none exists
     */
    @Override
    public Ticket getSuitableJourney(String arrivalStation, LocalTime passengerTime) {
        Iterator<Map.Entry<LocalTime,List<Journey>>> it = stationsOrderedByHour.iterator();
        while (it.hasNext()){
            Map.Entry<LocalTime, List<Journey>> entry = it.next();
            if (!entry.key().isBefore(passengerTime)){
               Iterator<Journey> journeyIterator = entry.value().iterator();
               while (journeyIterator.hasNext()){
               Journey journey = journeyIterator.next();
               Stops destinationStop = journey.getStop(arrivalStation);

               if (destinationStop != null) {

                   return new Ticket(journey,this,journey.getStop(name).getDepartureTime(),
                           destinationStop.getStation(),destinationStop.getArrivalTime(),0);
                 }
               }

            }
        }

      return null;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the sequential position of the station on the line.
     *
     * @return station line position
     */
    @Override
    public int getLinePosition() {
            return linePosition;
        }
    /**
     * Time Complexity:
     * Best Case: O(1) - Tree is empty or the departure time matches the root node, appending the journey to the tail of the list in constant time.
     * Worst Case: O(n) - Traverses down the height n of the BSTree to find or insert the departure time
     * Registers a journey departure at the specified time for this station.
     *
     * @param time scheduled departure time
     * @param journey journey departing at the given time
     */
    @Override
    public void addDeparture(LocalTime time, Journey journey){
        List<Journey> journeyList = stationsOrderedByHour.get(time);
        if(journeyList  == null){
            journeyList = new SinglyLinkedList<>();
            stationsOrderedByHour.put(time,journeyList);
        }
        journeyList.addLast(journey);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Tree is empty or the root has no left child and its departure time is already after maxTime, terminating immediately.
     * Worst Case: O(n^2) - All n departures fall within the [minTime, maxTime] window, requiring getStop and betterJourney to scan all n stops of each journey.
     * Finds the journey with the fewest stops between this station and arrivalStation within the given time interval.
     *
     * @param arrivalStation name of the destination station
     * @param minTime earliest acceptable departure time
     * @param maxTime latest acceptable departure time
     * @return ticket for the best matching journey, or null if none is found
     */
    @Override
    public Ticket getSuitableJourneyMinStops(String arrivalStation, LocalTime minTime, LocalTime maxTime) {
        Journey bestJourney = null;
        Iterator<Map.Entry<LocalTime,List<Journey>>> it = stationsOrderedByHour.iterator();
        boolean withinInterval = true;

        while (it.hasNext() && withinInterval) {
            Map.Entry<LocalTime, List<Journey>> entry = it.next();

            if (entry.key().isAfter(maxTime)) {
                withinInterval = false;
            } else if (!entry.key().isBefore(minTime)){
                Iterator<Journey> jIt = entry.value().iterator();
                while (jIt.hasNext()) {
                    Journey journey = jIt.next();
                    Stops arrivalStop = journey.getStop(arrivalStation);
                    Stops departureStop = journey.getStop(this.name);

                    if (arrivalStop != null && departureStop != null) {
                        if (bestJourney == null || betterJourney(journey, bestJourney, arrivalStation)) {
                            bestJourney = journey;
                        }
                    }
                }
            }

        }
        if (bestJourney == null) {
            return null;
        }
        return createTicket(bestJourney, arrivalStation);
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Finds this station at the first stop of the journey, locates the departure time at the root of the BSTree, and removes the journey from the head of the list in constant time.
     * Worst Case: O(n) - Scans all n stops of the journey via getStop, traverses a skewed BSTree of height n, and searches/removes the journey linearly across up to n entries in the list.
     * Removes a journey from this station's departure schedule.
     *
     * @param journey journey to be removed
     */
    @Override
    public void removeJourney(Journey journey) {
        Stops stop = journey.getStop(this.name);

        if (stop != null && stop.getDepartureTime() != null) {
            LocalTime depTime = stop.getDepartureTime();

            List<Journey> list = stationsOrderedByHour.get(depTime);
            if (list != null) {
                int index = list.indexOf(journey);
                if (index != -1) {
                    list.remove(index);
                }

                if (list.isEmpty()) {
                    stationsOrderedByHour.remove(depTime);
                }
            }
        }
    }


    /**
     * Time Complexity:
     * Best Case: O(n) - Unconditionally traverses all n stops of both journeys inside getStopsCountBetween to compare stop counts.
     * Worst Case: O(n) - Scans all n stops of both journeys via getStop and getStopsCountBetween to compare stop counts, arrival times, and departure times.
     * Compares two candidate journeys to determine whether the current journey is preferable to the best one found so far.
     *
     * @param current candidate journey being evaluated
     * @param best best journey found so far
     * @param arrivalStation name of the destination station
     * @return true if current is better than best, false otherwise
     */
    private boolean betterJourney(Journey current, Journey best, String arrivalStation) {

        Stops currentDep = current.getStop(this.name);
        Stops currentArr = current.getStop(arrivalStation);
        int currentStops = current.getStopsCountBetween(this.name, arrivalStation);

        Stops bestDep = best.getStop(this.name);
        Stops bestArr = best.getStop(arrivalStation);
        int bestStops = best.getStopsCountBetween(this.name, arrivalStation);

        if (currentStops != bestStops) {
            return currentStops < bestStops;
        }

        LocalTime currentArrTime = currentArr.getArrivalTime();
        LocalTime bestArrTime = bestArr.getArrivalTime();
        if (!currentArrTime.equals(bestArrTime)) {
            return currentArrTime.isBefore(bestArrTime);
        }

        LocalTime currentDepTime = currentDep.getDepartureTime();
        LocalTime bestDepTime = bestDep.getDepartureTime();
        if (!currentDepTime.equals(bestDepTime)) {
            return currentDepTime.isBefore(bestDepTime);
        }
        return false;
    }

    /**
     * Time Complexity:
     * Best Case: O(n) - Unconditionally iterates through all n stops of bestJourney in getStopsCountBetween to compute the stop count.
     * Worst Case: O(n) - Scans all n stops of bestJourney via getStop and getStopsCountBetween before instantiating and attaching the ticket.
     * Creates and attaches a ticket to the selected best journey between this station and the arrival station.
     *
     * @param bestJourney selected optimal journey
     * @param arrivalStation name of the destination station
     * @return created Ticket object for the journey
     */
    private Ticket createTicket(Journey bestJourney, String arrivalStation) {

        Stops departureStop = bestJourney.getStop(this.name);
        Stops arrivalStop = bestJourney.getStop(arrivalStation);

        int stopsCount = bestJourney.getStopsCountBetween(this.name, arrivalStation);


        bestJourney.setJourneyTicket(bestJourney, departureStop.getStation(), departureStop.getDepartureTime(),
                arrivalStop.getStation(), arrivalStop.getArrivalTime(), stopsCount
        );

        return bestJourney.getJourneyTicket();
    }
}


