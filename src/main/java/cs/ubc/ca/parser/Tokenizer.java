package cs.ubc.ca.parser;

import cs.ubc.ca.errors.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

    private static String program;

    private String[] tokens;

    private int currentToken;

    private int line;

    private int column;

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
        this.tokens = this.preProcessInput();
        this.currentToken = 0;
        this.line = 1;
        this.column = 0;
    }

    // https://stackoverflow.com/questions/4042434/converting-arrayliststring-to-string-in-java
    private String[] preProcessInput(){
        String[] result = this.program.replace(System.lineSeparator(), String.format(" %s ", System.lineSeparator())).split(" ");
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> notEmpty = isEmpty.negate();
        Stream<String> stream = Arrays.asList(result).stream();
        List<String> filtered = stream.filter(notEmpty).collect(Collectors.toList());
        return filtered.toArray(new String[0]);
    }

    public String top() {
        if (currentToken < tokens.length) {
            while (System.lineSeparator().equals(tokens[currentToken])) {
                currentToken++;
                this.line++;
                this.column = 0;
            }

            return tokens[currentToken];
        }

        return null;
    }

    public String pop() {
        if (this.top() != null) {
            String token = tokens[currentToken];
            currentToken++;
            this.column++;
            return token;
        }
        return null;
    }

    public boolean hasNext() {
        return this.top() != null;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn(){
        return this.column;
    }
}
