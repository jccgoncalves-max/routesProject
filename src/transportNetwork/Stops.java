package transportNetwork;

import java.time.LocalTime;

public interface Stops {
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
     * @return
     */
    LocalTime getArrivalTime();
}
