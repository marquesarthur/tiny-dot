package cs.ubc.ca.errors;

import java.io.IOException;

public class ParseException extends RuntimeException {

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Exception e) {
        super(msg, e);

    }
}
