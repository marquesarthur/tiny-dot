package cs.ubc.ca.ui;



import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        // This is our context variable from the interpreter pattern
        DotProgram program = new DotProgram("valid/input.tdot");

        program.compile();
    }
}
