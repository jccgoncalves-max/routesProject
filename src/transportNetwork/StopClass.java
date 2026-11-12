package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents each stop of a Journey.
 */
public class StopClass implements Stops, Serializable {
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
     * StopClass Constructor
     * @param journey Journey which stop belongs to.
     * @param departureTime Stop departure time
     * @param arrivalTime Stop arrival time
     */
    protected StopClass(Station station,Journey journey,LocalTime arrivalTime, LocalTime departureTime){
        this.station = station;
        this.journey = journey;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    @Override
    public Station getStation() {
            return station;
    }
    @Override
    public Journey getJourney() {
        return journey;
    }
    @Override
    public LocalTime getDepartureTime() {
        return departureTime;
    }
    @Override
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }
}
