package cs.ubc.ca.parser;


import cs.ubc.ca.ast.Edge;
import cs.ubc.ca.dsl.OutputWriter;
import cs.ubc.ca.errors.ParseException;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Class that represents an EdgeNode in the AST representation of the DSL.
 * An EdgeNode matches the expression:
 *
 *      edge_node ::= 'connect' identifier 'to' identifier
 *      identifier ::= [_A-Za-z]+([A-Za-z0-9]*)
 *
 * </pre>
 *
 * @author Arthur Marques
 */
public class EdgeNode extends Node {

    private List<String> expression;

    private Edge edge;

    public EdgeNode() {
        super();
        this.expression = Arrays.asList(Tokens.CONNECT, Tokens.IDENTIFIER, Tokens.TO, Tokens.IDENTIFIER);
        this.edge = new Edge();
    }

    @Override
    public void parse(Tokenizer context) {
        // TODO: method stub
    }

    @Override
    public void compile() {
        // TODO: method stub
    }

    public Edge getEdge() {
        return this.edge;
    }
}
