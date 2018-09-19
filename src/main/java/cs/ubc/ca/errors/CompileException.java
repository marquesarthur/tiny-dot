package cs.ubc.ca.errors;

public class CompileException extends RuntimeException {

    public CompileException(String msg) {
        super(msg);
    }

    public CompileException(String msg, Exception e) {
        super(msg, e);

    }
}
