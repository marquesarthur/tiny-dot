package cs.ubc.ca.parser;


import cs.ubc.ca.dsl.IProgram;
import cs.ubc.ca.dsl.OutputWriter;
import cs.ubc.ca.errors.ParseException;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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
public class EdgeNode {

    protected String target;

    protected List<Node> children;

    private Edge edge;

    public EdgeNode() {
        super();
        this.edge = new Edge();
    }

    public String getTarget() {
        try {
            URI resourcePathFile = System.class.getResource(this.target).toURI();
            String resourcePath = Files.readAllLines(Paths.get(resourcePathFile)).get(0);
            URI rootURI = new File("").toURI();
            URI resourceURI = new File(resourcePath).toURI();
            URI relativeResourceURI = rootURI.relativize(resourceURI);
            return relativeResourceURI.getPath();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses the content of a tokenizer generating the current edge node.
     * Edge node has no children.
     *
     * @param context
     */
    public void parse(Tokenizer context) {
        int currentLine = context.getLine();

        for (String exp: new String[]{"connect", "[_A-Za-z]+([A-Za-z0-9]*)", "to", "[_A-Za-z]+([A-Za-z0-9]*)"}){
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)){ //checking
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (exp.equals("[_A-Za-z]+([A-Za-z0-9]*)") && token.matches("[_A-Za-z]+([A-Za-z0-9]*)")) {
                this.edge.connect(token);
            }
        }
    }

    public Edge getEdge() {
        return this.edge;
    }

    public void compile() {
        PrintWriter writer = OutputWriter.getInstance().getWriter();
        writer.println(edge.toDigraph());
    }


    public class Edge {

        private String a;

        private String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public void connect(String token) {
            if (this.a == null) {
                this.setA(token);
            } else {
                this.setB(token);
            }
        }

        public String toDigraph(){
            return String.format("%s->%s" + System.lineSeparator(), a, b);
        }
    }

}
