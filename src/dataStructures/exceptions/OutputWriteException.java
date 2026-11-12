package dataStructures.exceptions;

public class OutputWriteException extends RuntimeException {
    public OutputWriteException(String message) {
        super(message);
    }
    public OutputWriteException() {
        super();
    }
}
