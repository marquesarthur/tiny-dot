package cs.ubc.ca.parser;

import cs.ubc.ca.ast.Shape;
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

    @Override
    public void parse(Tokenizer context) {
        // TODO: method stub
    }

    @Override
    public void compile() {
        // TODO: method stub
    }

    public Shape getShape() {
        return this.shape;
    }

}
