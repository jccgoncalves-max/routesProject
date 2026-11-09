package dataStructures.exceptions;

public class JourneyAlreadyExistException extends RuntimeException {
    public JourneyAlreadyExistException(String message) {
        super(message);
    }
    public JourneyAlreadyExistException() {
        super();
    }
}
