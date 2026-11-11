package transportNetwork;

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
}
