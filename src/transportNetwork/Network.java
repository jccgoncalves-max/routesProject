package transportNetwork;


import java.io.Serializable;
/**
 * Represents the transport network, managing stations and journeys.
 */
public interface Network extends Serializable {
    /**
     * Checks if journey with given id already exist
     * @param id Journey id
     * @return true if journey exists
     */
    boolean hasJourney(String id);

    /**
     * Adds stops onto a journey with given id
     * @param journeyId Journey id
     * @param stationsName Name of the station
     * @param arrivals Array of arrival times
     * @param departures Array of departure times
     */
    void addJourney(String journeyId, String[] stationsName, String[] arrivals, String[] departures );


    /**
     * Finds suitable journey ticket for passenger
     * @param departureStation Name of the departure station
     * @param arrivalStation Name of the arrival station
     * @param passengerTime Time that passenger arrives at station
     * @return Suitable journey ticket
     */

    Ticket findJourney(String departureStation, String arrivalStation, String passengerTime);

    /**
     * Finds suitable journey with minimumTime stops
     * @param departureStation Name of the departure station
     * @param arrivalStation Name of the arrival station
     * @param minimumTime Minimum time of the time interval
     * @param maximumTime Maximum time of the time interval
     * @return Suitable journey ticket
     */
    Ticket findJourneyMinStops(String departureStation,String arrivalStation,String minimumTime,String maximumTime);

    /**
     * Adds a new station to the transport network line.
     * @param nameStation name of the station to be added
     */
    void addStation(String nameStation); // usado na leitura do ficheiro

    /**
     * Removes the journey with the given identifier from the network.
     * @param journeyId identifier of the journey to remove /
     * @return true if the journey existed and was removed, false otherwise
     */
    boolean removeJourney(String journeyId);

    /**
     * Saves the current state of the transport network to a file
     * @param fileName name of the file where the network will be stored
     */
    void storeCities(String fileName);


}
