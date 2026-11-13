package transportNetwork;

import java.time.LocalTime;
/**
 * Represents a travel ticket containing journey details, departure and arrival stations,
 * scheduled times, and the number of intermediate stops.
 * Space Complexity:
 * O(1) - Stores fixed object references and a primitive integer without any dynamic collections.
 *
 * @param journey journey associated with the ticket
 * @param departureStation station where the passenger departs
 * @param departureTime scheduled departure time at the departure station
 * @param arrivalStation station where the passenger arrives
 * @param arrivalTime scheduled arrival time at the arrival station
 * @param stop number of stops between the departure and arrival stations
 */
public record Ticket(Journey journey, Station departureStation, LocalTime departureTime,Station arrivalStation, LocalTime arrivalTime,int stop) {

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field access in constant time.
     * Worst Case: O(1) - Direct field access without loops.
     * Returns the journey associated with this ticket.
     *
     * @return ticket journey
     */
    @Override
    public Journey journey() {
        return journey;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field access in constant time.
     * Worst Case: O(1) - Direct field access without loops.
     * Returns the departure station of this ticket.
     *
     * @return departure station
     */
    @Override
    public Station departureStation() {
        return departureStation;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field access in constant time.
     * Worst Case: O(1) - Direct field access without loops.
     * Returns the scheduled departure time of this ticket.
     *
     * @return departure time
     */
    @Override
    public LocalTime departureTime() {
        return departureTime;
    }

    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field access in constant time.
     * Worst Case: O(1) - Direct field access without loops.
     * Returns the arrival station of this ticket.
     *
     * @return arrival station
     */
    @Override
    public Station arrivalStation() {
        return arrivalStation;
    }
    /**
     * Time Complexity:
     * Best Case: O(1) - Direct field access in constant time.
     * Worst Case: O(1) - Direct field access without loops.
     * Returns the scheduled arrival time of this ticket.
     *
     * @return arrival time
     */
    @Override
    public LocalTime arrivalTime() {
        return arrivalTime;
    }
}
