package cs.ubc.ca.errors;

public class TransformationException extends RuntimeException {
    public TransformationException(String msg, Exception e) {
        super(msg, e);
    }
}
