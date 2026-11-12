package transportNetwork;


public interface Network {

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

}
