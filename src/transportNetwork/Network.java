package transportNetwork;


public interface Network {
    /**
     * Adds Journey into the transportation network system.
     * @param journeyId
     */
    void addJourney(String journeyId);

    /**
     * Checks if journey with given id already exist
     * @param id
     * @return true if exists
     */
    boolean hasJourney(String id);

    /**
     * Adds stops onto a journey with given id
     * @param journeyId
     * @param stationName
     * @param departureTime
     * @param arrivalTime
     */
    void addStopToJourney(String journeyId, String stationName, String departureTime, String arrivalTime);
}
