package transportNetwork;

import java.time.LocalTime;

public record Ticket(Journey journey, Station DepartureStation, LocalTime DepartureTime,Station arrivalStation, LocalTime arrivalTime,int stop) {

    @Override
    public Journey journey() {
        return journey;
    }

    @Override
    public Station DepartureStation() {
        return DepartureStation;
    }

    @Override
    public LocalTime DepartureTime() {
        return DepartureTime;
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
