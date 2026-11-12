package transportNetwork;
import java.io.Serializable;
import java.time.*;
import java.util.*;
/**
 * Represents a station on the line.
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
     * Station Constructor
     * @param name Station unique name
     * @param linePosition Station line position
     */
    protected StationClass(String name,int linePosition) {
        this.name = name;
        this.linePosition = linePosition;
        stationsOrderedByHour = new TreeMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Ticket getSuitableJourney(String arrivalStation, LocalTime passengerTime) {
        for(Map.Entry<LocalTime, List<Journey>> e : stationsOrderedByHour.tailMap(passengerTime).entrySet()){
            for(Journey journey : e.getValue()){
                Stops destinationStop = journey.getStop(arrivalStation);
                if(destinationStop != null)
                    return new Ticket(journey,this,journey.getStop(name).getDepartureTime(),
                            destinationStop.getStation(),destinationStop.getArrivalTime(),0);
            }
        }
        return null;
    }
    @Override
    public int getLinePosition() {
            return linePosition;
        }

    @Override
    public void addDeparture(LocalTime time, Journey journey){
        List<Journey> journeyList = stationsOrderedByHour.get(time);
        if(journeyList  == null){
            journeyList = new LinkedList<>();
            stationsOrderedByHour.put(time,journeyList);
        }
        journeyList.add(journey);
    }

    @Override
    public Ticket getSuitableJourneyMinStops(String arrivalStation, LocalTime minTime, LocalTime maxTime) {
        Journey bestJourney = null;
        Iterator<Map.Entry<LocalTime,List<Journey>>> it = stationsOrderedByHour.tailMap(minTime).entrySet().iterator();
        boolean withinInterval = true;

        while (it.hasNext() && withinInterval) {
            Map.Entry<LocalTime, List<Journey>> entry = it.next();

            if (entry.getKey().isAfter(maxTime)) {
                withinInterval = false;
            } else {
                for (Journey journey : entry.getValue()) {
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

    @Override
    public void removeJourney(Journey journey) {
        Stops stop = journey.getStop(this.name);

        if (stop != null && stop.getDepartureTime() != null) {
            LocalTime depTime = stop.getDepartureTime();

            List<Journey> list = stationsOrderedByHour.get(depTime);
            if (list != null) {
                list.remove(journey);

                if (list.isEmpty()) {
                    stationsOrderedByHour.remove(depTime);
                }
            }
        }
    }




    /**
     *
     * @param current
     * @param best
     * @param arrivalStation
     * @return
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
        //chegada mais cedo
        LocalTime currentArrTime = currentArr.getArrivalTime();
        LocalTime bestArrTime = bestArr.getArrivalTime();
        if (!currentArrTime.equals(bestArrTime)) {
            return currentArrTime.isBefore(bestArrTime);
        }
        //partida mais cedo
        LocalTime currentDepTime = currentDep.getDepartureTime();
        LocalTime bestDepTime = bestDep.getDepartureTime();
        if (!currentDepTime.equals(bestDepTime)) {
            return currentDepTime.isBefore(bestDepTime);
        }
        return false;
    }

    /**
     *
     * @param bestJourney
     * @param arrivalStation
     * @return
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


