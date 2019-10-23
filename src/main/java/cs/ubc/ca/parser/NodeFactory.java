package cs.ubc.ca.parser;

import cs.ubc.ca.errors.ParseException;

import static cs.ubc.ca.parser.Tokens.*;

public class NodeFactory {

    public static Node getParser(String nextToken) {
        switch (nextToken) {
            case MAKE:
                return new ShapeNode();

            case CONNECT:
                return new EdgeNode();

            default:
                throw new ParseException("Unrecognizable token: " + nextToken);
        }
    }
}

