package cs.ubc.ca.errors;

public class CompileError extends RuntimeException {

    public CompileError(String msg) {
        super(msg);
    }

    public CompileError(String msg, Exception e) {
        super(msg, e);
    }
}
