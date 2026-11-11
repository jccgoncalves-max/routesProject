package transportNetwork;

import java.time.LocalTime;

public interface Station {

    String getName();

    /**
     * Returns suitable station for passenger
     *
     * @param arrivalStation Arrival Station
     * @param passengerTime  Passenger departure time
     * @return
     */
    Ticket getSuitableJourney(String arrivalStation, LocalTime passengerTime);

    int getLinePosition();

    void addDeparture(LocalTime time, Journey journey);
}
