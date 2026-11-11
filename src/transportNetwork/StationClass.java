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
   // private int linePosition;
    /**
     * Tree of stations ordered by hour
     */
    private SortedMap<LocalTime,List<Journey>>stationsOrderedByHour;

    /**
     * Station Constructor
     * @param name Station unique name
     //* @param linePosition Station line position
     */
    public StationClass(String name) {
        this.name = name;
        //this.linePosition = linePosition;
        stationsOrderedByHour = new TreeMap<>();
    }


    @Override
    public void getSuitableJourney(String arrivalStation, LocalTime passengerTime) {

    }
}


