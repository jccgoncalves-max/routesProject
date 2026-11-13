package transportNetwork;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents a stop made by a journey at a specific station.
 */
public interface Stops extends Serializable {
    /**
     * Returns stop station
     * @return stop station
     */
    Station getStation();

    /**
     * Return stop Journey
     * @return stop journey
     */
    Journey getJourney();

    /**
     * Return stop Departure time
     * @return stop journey
     */
    LocalTime getDepartureTime();

    /**
     * Returns stop arrival time
     * @return stop arrival time
     */
    LocalTime getArrivalTime();
}
