package transportNetwork;

import java.time.LocalTime;

public record Ticket(Journey journey, Station departureStation, LocalTime departureTime,Station arrivalStation, LocalTime arrivalTime,int stop) {

    @Override
    public Journey journey() {
        return journey;
    }

    @Override
    public Station departureStation() {
        return departureStation;
    }

    @Override
    public LocalTime departureTime() {
        return departureTime;
    }

    @Override
    public Station arrivalStation() {
        return arrivalStation;
    }

    @Override
    public LocalTime arrivalTime() {
        return arrivalTime;
    }
}
