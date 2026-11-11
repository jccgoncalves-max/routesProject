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
                            j.getArrivalStation().getStation(),j.getArrivalStation().getArrivalTime());
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
}


