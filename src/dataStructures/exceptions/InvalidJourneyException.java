package dataStructures.exceptions;

public class InvalidJourneyException extends RuntimeException {
    public InvalidJourneyException(String message) {
        super(message);
    }
    public InvalidJourneyException() {
        super();
    }
}
