package dataStructures.exceptions;

public class InvalidStationOrderException extends RuntimeException {
    public InvalidStationOrderException(String message) {
        super(message);
    }
    public InvalidStationOrderException() {
        super();
    }
}
