package transportNetwork;
import java.time.*;
import java.util.*;
/**
 * Represents a station on the line.
 */
public class StationClass implements Station {
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
     //* @param linePosition Station line position
     */
    public StationClass(String name,int linePosition) {
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
            for(Journey j : e.getValue()){
                if(j.getArrivalStation().getStation().getName().equalsIgnoreCase(arrivalStation))
                    /*
                    j.setJourneyTicket(j,j.getDepartureStation().getStation(),j.getDepartureStation().getDepartureTime(),
                            j.getArrivalStation().getStation(),j.getArrivalStation().getArrivalTime());
                return j.getJourneyTicket();
                     */
                    return new Ticket(j,j.getDepartureStation().getStation(),j.getDepartureStation().getDepartureTime(),
                            j.getArrivalStation().getStation(),j.getArrivalStation().getArrivalTime(),0);
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
        // Filtrar partidas no intervalo minTime, maxTime
        SortedMap<LocalTime, List<Journey>> journeyFiltre = stationsOrderedByHour.subMap(minTime, maxTime);

        Journey bestJourney = null;

        for (List<Journey> listJourney : journeyFiltre.values()) {
            for (Journey journey : listJourney) {

                Stops arrStop = journey.getStopAt(arrivalStation);
                Stops depStop = journey.getStopAt(this.name);

                if (arrStop != null && depStop != null) {
                    if (depStop.getDepartureTime().isBefore(arrStop.getArrivalTime())) {
                        if (bestJourney == null) {
                            bestJourney = journey;
                        }
                        else if (betterJourney(journey, bestJourney, arrivalStation)) {
                            bestJourney = journey;

                        }

                    }
                }
            }
        }
        if (bestJourney == null) {
            return null;
        }

        return creatTicket(bestJourney, arrivalStation);
    }
    private boolean betterJourney(Journey current, Journey best, String arrivalStation) {

        Stops currentDep = current.getStopAt(this.name);
        Stops currentArr = current.getStopAt(arrivalStation);
        int currentStops = current.getStopsCountBetween(this.name, arrivalStation);

        Stops bestDep = best.getStopAt(this.name);
        Stops bestArr = best.getStopAt(arrivalStation);
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

    private Ticket creatTicket(Journey bestJourney, String arrivalStation) {

        Stops depStop = bestJourney.getStopAt(this.name);
        Stops arrStop = bestJourney.getStopAt(arrivalStation);

        // Calcular o número de paragens
        int stopsCount = bestJourney.getStopsCountBetween(this.name, arrivalStation);


        bestJourney.setJourneyTicket(
                bestJourney,
                depStop.getStation(),
                depStop.getDepartureTime(),
                arrStop.getStation(),
                arrStop.getArrivalTime(),
                stopsCount
        );


        return bestJourney.getJourneyTicket();
    }
}


