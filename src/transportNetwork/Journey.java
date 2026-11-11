package transportNetwork;

import java.time.LocalTime;

public interface Journey {
    /**
     * Adds a new stop to the journey
     * @param newStop
     */
    void addStop(Stops newStop);

    String getId();

    /**
     * Returns Journey ticket
     * @return Journey ticket
     */
    Ticket getJourneyTicket();


    Stops getDepartureStation();

    Stops getArrivalStation();

    void setJourneyTicket(Journey journey, Station departure, LocalTime departureTime, Station arrival, LocalTime arrivalTime, int stopCount);

    void setJourneyTicket(Journey journey, Station departure, LocalTime departureTime, Station arrival, LocalTime arrivalTime);

    Stops getStopAt(String stationName);

    int getStopsCountBetween(String originStation, String destinationStation);

}
