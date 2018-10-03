package cs.ubc.ca.parser;


import cs.ubc.ca.ast.Edge;
import cs.ubc.ca.errors.ParseException;

import java.util.Arrays;
import java.util.List;

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
        int currentLine = context.getLine();
        for (String exp: this.expression){
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.\nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)){
                throw new ParseException(String.format("\"Invalid token at line %s.\nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
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
        writer.println(edge.toDigraph());
    }
}
