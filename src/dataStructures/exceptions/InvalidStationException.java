package dataStructures.exceptions;

public class InvalidStationException extends RuntimeException {
    public InvalidStationException(String message) {
        super(message);
    }

    public InvalidStationException() {
        super();
    }
}
