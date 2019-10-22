package cs.ubc.ca.parser;

import cs.ubc.ca.errors.ParseException;

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

/**
 * Tokenizer class. Tokenizes an input file, in case the file exists.
 * The tokenizer acts as a FIFO data structure where tokens are served in the order that they were processed.
 *
 * @author Arthur Marques
 */
public class Tokenizer {

    private String program;

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

    /**
     * pre process input string removing empty lines.
     * for a more robust dsl, you could also add pre-processing of comments
     *
     * @return
     */
    private String[] preProcessInput() {
        String[] result = this.program.replace(System.lineSeparator(), String.format(" %s ", System.lineSeparator())).split(" ");
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> notEmpty = isEmpty.negate();
        Stream<String> stream = Arrays.asList(result).stream();
        List<String> filtered = stream.filter(notEmpty).collect(Collectors.toList());
        return filtered.toArray(new String[0]);
    }

    /**
     * Returns the top token in the queue. Does not remove the token from the queue.
     *
     * @return string first token in the queue.
     */
    public String top() {
        if (currentToken < tokens.length) {
            // ignore blank lines
            while (System.lineSeparator().equals(tokens[currentToken])) {
                currentToken++;
                this.line++;
                this.column = 0;
            }

            return tokens[currentToken];
        }

        return null;
    }

    /**
     * Returns the top token in the queue. Removes the token from the queue.
     *
     * @return string first token in the queue.
     */
    public String pop() {
        if (this.top() != null) {
            String token = tokens[currentToken];
            currentToken++;
            this.column++;
            return token;
        }
        return null;
    }

    /**
     * Checks whether there are more tokens for processing
     *
     * @return
     */
    public boolean hasNext() {
        return this.top() != null;
    }

    /**
     * Returns the current line in the input file based on the tokens that have been processed.
     * Useful for debugging and generating user friendly parsing errors.
     *
     * @return int of current line in the tokenizer
     */
    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }
}
