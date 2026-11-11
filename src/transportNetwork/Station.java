package transportNetwork;

import java.time.LocalTime;

public interface Station {

    /**
     * Returns suitable station for passenger
     * @param arrivalStation Arrival Station
     * @param passengerTime Passenger departure time
     */
    void getSuitableJourney(String arrivalStation, LocalTime passengerTime);
}
