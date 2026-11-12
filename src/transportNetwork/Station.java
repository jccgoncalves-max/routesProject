package transportNetwork;

import java.time.LocalTime;

public interface Station {
    /**
     *
     * @return
     */
    String getName();

    /**
     * Returns suitable station for passenger
     *
     * @param arrivalStation Arrival Station
     * @param passengerTime  Passenger departure time
     * @return
     */
    Ticket getSuitableJourney(String arrivalStation, LocalTime passengerTime);

    /**
     *
     * @return
     */
    int getLinePosition();

    /**
     *
     * @param time
     * @param journey
     */
    void addDeparture(LocalTime time, Journey journey);

    /**
     *
     * @param arrivalStation
     * @param minTime
     * @param maxTime
     * @return
     */
    Ticket getSuitableJourneyMinStops(String arrivalStation, LocalTime minTime,LocalTime maxTime);

}
