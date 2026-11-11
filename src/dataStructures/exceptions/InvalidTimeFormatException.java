package dataStructures.exceptions;

public class InvalidTimeFormatException extends RuntimeException {
    public InvalidTimeFormatException(String message) {
        super(message);
    }
    public InvalidTimeFormatException() {
        super();
    }
}
