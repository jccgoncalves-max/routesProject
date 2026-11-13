package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents each stop of a Journey.
 * Space Complexity:
 * O(1) - Stores fixed object references for the station, journey, arrival time,
 * and departure time without any dynamic collections.
 */
public class StopClass implements Stops, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Station that references the stop
     */
    private Station station;
    /**
     * Journey which stop belongs to.
     */
    private Journey journey;

    /**
     * Departure time
     */
    private LocalTime departureTime;

    /**
     * Arrival time
     */
    private LocalTime arrivalTime;

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field assignments in constant time without loops.
     * Worst Case: O(1) - Constant time initialization.
     * StopClass Constructor.
     *
     * @param station Station that references the stop
     * @param journey Journey which stop belongs to
     * @param arrivalTime Stop arrival time
     * @param departureTime Stop departure time
     */
    protected StopClass(Station station,Journey journey,LocalTime arrivalTime, LocalTime departureTime){
        this.station = station;
        this.journey = journey;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the station associated with this stop.
     *
     * @return station of this stop
     */
    @Override
    public Station getStation() {
            return station;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the journey associated with this stop.
     *
     * @return journey of this stop
     */
    @Override
    public Journey getJourney() {
        return journey;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the scheduled departure time at this stop.
     *
     * @return departure time, or null if this is the final station
     */
    @Override
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct attribute access in constant time.
     * Worst Case: O(1) - Direct attribute access without loops.
     * Returns the scheduled arrival time at this stop.
     *
     * @return arrival time, or null if this is the starting station
     */
    @Override
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
}
