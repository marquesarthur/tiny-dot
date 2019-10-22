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
public class ShapeNode {

    private List<String> expression;

    private Shape shape;

    protected String target;

    protected List<Node> children;

    public ShapeNode() {
        super();
        this.shape = new Shape();
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
     * Parses the content of a tokenizer generating the current shape node.
     * Shape node has no children.
     *
     * @param context
     */
    public void parse(Tokenizer context) {
        int currentLine = context.getLine();
        for (String exp: new String[]{"make", "me", "a", "circle|square", "called", "[_A-Za-z]+([A-Za-z0-9]*)", "please"}){
            String token = context.pop();
            if (token == null) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (!token.matches(exp)) {
                throw new ParseException(String.format("Invalid token at line %s.%nParser was expecting: [%s] and received: [%s] instead", currentLine, exp, token));
            }
            if (token.matches("circle|square")) {
                this.shape.setGeoShape(token);
            }
            if (exp.equals("[_A-Za-z]+([A-Za-z0-9]*)") && token.matches("[_A-Za-z]+([A-Za-z0-9]*)")) {
                this.shape.setName(token);
            }
        }
    }

    public Shape getShape() {
        return this.shape;
    }

    public void compile() {
        PrintWriter writer = OutputWriter.getInstance().getWriter();
        writer.println(shape.toDigraph());
    }


    public class Shape {

        private String geoShape;

        private String name;


        public String getGeoShape() {
            return geoShape;
        }

        public void setGeoShape(String geoShape) {
            this.geoShape = geoShape;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toDigraph() {
            return String.format("%s[shape=%s]" + System.lineSeparator(), name, geoShape);
        }
    }

}
