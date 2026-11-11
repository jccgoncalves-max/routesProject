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
     *
     * @param journeyId
     * @param stationsName
     * @param departures
     * @param arrivals
     */
    void addJourney(String journeyId, String[] stationsName, String[] departures, String[] arrivals);


    /**
     *
     * @param departureStation
     * @param arrivalStation
     * @param passengerTime
     * @return
     */

    Ticket findJourney(String departureStation, String arrivalStation, String passengerTime);

    /**
     *
     * @param departureStation
     * @param arrivalStation
     * @param miniumTime
     * @param maximumTime
     * @return
     */
    Ticket findJourneyMinStops(String departureStation,String arrivalStation,String miniumTime,String maximumTime);

}
