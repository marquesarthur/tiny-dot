package cs.ubc.ca.parser;

import cs.ubc.ca.ast.Shape;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.parser.Node;

import java.util.Arrays;
import java.util.List;

public class ShapeNode extends Node {

    private List<String> expression;

    private Shape shape;

    public ShapeNode() {
        super();
        this.expression = Arrays.asList(Tokens.MAKE, Tokens.ME, Tokens.A, Tokens.SHAPE, Tokens.CALLED, Tokens.IDENTIFIER, Tokens.PLEASE);
        this.shape = new Shape();
    }


    @Override
    public void parse(Tokenizer context) {
        int currentLine = context.getLine();
        for (String exp : this.expression) {
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.\nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)) {
                throw new ParseException(String.format("Invalid token at line %s.\nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (token.matches(Tokens.SHAPE)) {
                this.shape.setGeoShape(token);
            }
            if (exp.equals(Tokens.IDENTIFIER) && token.matches(Tokens.IDENTIFIER)) {
                this.shape.setName(token);
            }
        }
    }

    @Override
    public void compile() {
        writer.println(shape.toDigraph());
    }

    public Shape getShape() {
        return this.shape;
    }
}
