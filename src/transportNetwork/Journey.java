package transportNetwork;

import java.time.LocalTime;
import java.util.ArrayList;

public interface Journey {
    /**
     * Adds a new stop to the journey
     * @param newStop Stop to be added
     */
    void addStop(Stops newStop);

    /**
     * Returns Journey id
     * @return Journey id
     */
    String getId();

    /**
     * Returns Journey ticket
     * @return Journey ticket
     */
    Ticket getJourneyTicket();

    /**
     * Returns stop with given name
     * @param stationName name of the station
     * @return Stop with given name
     */
    Stops getStop(String stationName);

    /**
     * Creates a new Journey ticket
     * @param journey Journey
     * @param departureStation Name of the departure station
     * @param departureStationTime Departure time
     * @param arrivalStation Name of the arrivalStation station
     * @param arrivalStationTime Arrival time
     * @param stopCount Number of stops
     */
    void setJourneyTicket(Journey journey, Station departureStation, LocalTime departureStationTime, Station arrivalStation, LocalTime arrivalStationTime, int stopCount);

    /**
     * Counts the number of stops between two stations
     * @param originStation Name of the origin station
     * @param destinationStation Name of the destination station
     * @return Number of stops between two station
     */
    int getStopsCountBetween(String originStation, String destinationStation);

    /**
     *
     * @return
     */
    ArrayList<Stops> getStops();


}
