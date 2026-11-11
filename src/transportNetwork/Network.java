package transportNetwork;


public interface Network {


    /**
     * Checks if journey with given id already exist
     * @param id
     * @return true if exists
     */
    boolean hasJourney(String id);

    /**
     * Adds stops onto a journey with given id
     * @param journeyId Unique journey id
     * @param stationsName Array of the names of the stations
     * @param arrivals Array of the arrival times
     * @param departures Array of the departure times
     */
    void addJourney(String journeyId, String[] stationsName, String[] arrivals, String[] departures);


    /**
     * Finds suitable journey ticket for passenger
     * @param departureStation Name of the departure station
     * @param arrivalStation Name of the arrival station
     * @param passengerTime Time that passenger arrives at station
     * @return
     */
    Ticket findJourney(String departureStation, String arrivalStation, String passengerTime);

    /**
     * Removes station from system
     * @param journeyId Journey id
     */
    void removeJourney(String journeyId);
}
