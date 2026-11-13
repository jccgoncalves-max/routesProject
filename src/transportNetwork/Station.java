package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;
/**
 * Represents a station on the transport network line.
 */
public interface Station extends Serializable {
    /**
     * Returns the unique name of the station.
     * @return station name
     */
    String getName();

    /**
     * Returns suitable station for passenger
     *
     * @param arrivalStation Arrival Station
     * @param passengerTime  Passenger departure time
     * @return suitable journey ticket, or null if none exists
     */
    Ticket getSuitableJourney(String arrivalStation, LocalTime passengerTime);

    /**
     * Returns the position of the station on the line.
     * @return station position on the line
     */
    int getLinePosition();

    /**
     * Registers a journey departure at this station at the specified time.
     * @param time departure time
     * @param journey journey departing from this station
     */
    void addDeparture(LocalTime time, Journey journey);

    /**
     *Finds the suitable journey with the minimum number of stops within the given time interval.
     * @param arrivalStation  name of the destination station
     * @param minTime minimum departure time
     * @param maxTime maximum departure time
     * @return suitable journey ticket, or null if none exists
     */
    Ticket getSuitableJourneyMinStops(String arrivalStation, LocalTime minTime,LocalTime maxTime);

    /**
     * Removes a journey from this station's departures.
     * @param journey journey to be removed
     */
    void removeJourney(Journey journey);

}
