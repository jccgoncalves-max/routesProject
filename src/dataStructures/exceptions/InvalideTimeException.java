package dataStructures.exceptions;

public class InvalideTimeException extends RuntimeException {
    public InvalideTimeException(String message) {
        super(message);
    }

    public InvalideTimeException() {
        super();
    }
}
