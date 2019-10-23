package cs.ubc.ca.parser;


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

    /**
     * Parses the content of a tokenizer generating the current edge node.
     * Edge node has no children.
     *
     * @param context
     */
    @Override
    public void parse(Tokenizer context) {
        int currentLine = context.getLine();
        for (String exp: this.expression){
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)){ //checking
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (exp.equals(Tokens.IDENTIFIER) && token.matches(Tokens.IDENTIFIER)) {
                this.edge.connect(token);
            }
        }
    }

    public Edge getEdge() {
        return this.edge;
    }

    @Override
    public void compile() {
        PrintWriter writer = OutputWriter.getInstance().getWriter();
        writer.println(edge.toDigraph());
    }
}
