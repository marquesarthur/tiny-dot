package cs.ubc.ca.dsl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class OutputWriter {

    static OutputWriter instance;

    private PrintWriter writer;

    private OutputWriter(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter(file, encoding);

    }

    public static OutputWriter getInstance(File file, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        if (instance == null) {
            instance = new OutputWriter(file, encoding);
        }
        return OutputWriter.getInstance();
    }

    public static OutputWriter getInstance() {
        return instance;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public static void tearDown() {
        if (instance != null){
            instance = null;
        }
    }
}
