package transportNetwork;
/**
 * Represents each stop of a Journey.
 */
public class StopClass implements Stops {
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
    private String departureTime;

    /**
     * Arrival time
     */
    private String arrivalTime;

    /**
     * StopClass Constructor
     * @param journey Journey which stop belongs to.
     * @param departureTime Stop departure time
     * @param arrivalTime Stop arrival time
     */
    public StopClass(Station station,Journey journey,String departureTime, String arrivalTime){
        this.station = station;
        this.journey = journey;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
