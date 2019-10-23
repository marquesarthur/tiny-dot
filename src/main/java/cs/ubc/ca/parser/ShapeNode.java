package cs.ubc.ca.parser;

import cs.ubc.ca.dsl.OutputWriter;
import cs.ubc.ca.errors.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Class that represents a ShapeNode in the AST representation of the DSL.
 * A ShapeNode matches the expression:
 *
 *      shape_node ::= 'make' 'me' 'a' shape_type 'called' identifier 'please'
 *      shape_type ::= circle | square
 *      identifier ::= [_A-Za-z]+([A-Za-z0-9]*)
 *
 * </pre>
 *
 * @author Arthur Marques
 */
public class ShapeNode extends Node {

    private List<String> expression;

    private Shape shape;

    public ShapeNode() {
        super();
        this.expression = Arrays.asList(Tokens.MAKE, Tokens.ME, Tokens.A, Tokens.SHAPE, Tokens.CALLED, Tokens.IDENTIFIER, Tokens.PLEASE);
        this.shape = new Shape();
    }

    /**
     * Parses the content of a tokenizer generating the current shape node.
     * Shape node has no children.
     *
     * @param context
     */
    @Override
    public void parse(Tokenizer context) {
        int currentLine = context.getLine();
        for (String exp : this.expression) {
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (token.matches(Tokens.SHAPE)) {
                this.shape.setGeoShape(token);
            }
            if (exp.equals(Tokens.IDENTIFIER) && token.matches(Tokens.IDENTIFIER)) {
                this.shape.setName(token);
            }
        }
    }

    public Shape getShape() {
        return this.shape;
    }

    @Override
    public void compile() {
        PrintWriter writer = OutputWriter.getInstance().getWriter();
        writer.println(shape.toDigraph());
    }
}
