package cs.ubc.ca.parser;

import cs.ubc.ca.errors.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tokenizer {

    private static String program;

    private String[] tokens;

    private int currentToken;

    public Tokenizer(String filename) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Path filepath = Paths.get(classLoader.getResource(filename).toURI());
            this.program = new String(Files.readAllBytes(filepath), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new ParseException(String.format("Unable to load source: %s", filename), e);
        }
        tokenize();
    }

    private void tokenize() {
        this.program = this.program.replace(System.lineSeparator(), " ");
        this.tokens = this.program.split(" ");
        this.currentToken = 0;
    }

    public String top() {
        if (currentToken < tokens.length) {
            return tokens[currentToken];
        }

        return null;
    }

    public String pop() {
        if (this.top() != null) {
            String token = tokens[currentToken];
            currentToken++;
            return token;
        }
        return null;
    }

    public boolean hasNext() {
        return this.top() != null;
    }
}
