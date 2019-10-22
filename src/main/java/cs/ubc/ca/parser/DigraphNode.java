package cs.ubc.ca.parser;


import cs.ubc.ca.dsl.IProgram;
import cs.ubc.ca.dsl.OutputWriter;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.errors.TransformationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Class that represents a DigraphNode in the AST representation of the DSL.
 * A DigraphNode is the root node in the DSL and it match the expression:
 *
 *      digraph_node ::= (edge_node|shape_node)*
 *      edge_node ::= {@link EdgeNode}
 *      shape_node ::= {@link ShapeNode}
 *
 * </pre>
 *
 * @author Arthur Marques
 */
public class DigraphNode  {

    protected String target;

    public List<ShapeNode> shapeChildren;

    public List<EdgeNode> edgeChildren;

    public DigraphNode() {
        super();
    }

    /**
     * Parses the content of a tokenizer generating the current node.
     * Any line that matches a shape_node will add a {@link ShapeNode} child to the AST.
     * Any line that matches a edge_node will add a {@link EdgeNode} child to the AST.
     *
     * @param context
     */
    public void parse(Tokenizer context) {
        List<Object> nodes = new ArrayList<>();

        while (context.hasNext()) {
            String nextToken = context.top();
            switch (nextToken) {
                case "make":
                    ShapeNode shapeNode = new ShapeNode();
                    shapeNode.parse(context);
                    nodes.add(shapeNode);
                    break;
                case "connect":
                    EdgeNode edgeNode = new EdgeNode();
                    edgeNode.parse(context);
                    nodes.add(edgeNode);
                    break;
                default:
                    throw new ParseException("Unrecognizable token: " + nextToken);
            }
        }


        this.shapeChildren.addAll(nodes.stream().filter(n -> n instanceof ShapeNode).map(n -> (ShapeNode) n).collect(Collectors.toList()));
        this.edgeChildren.addAll(nodes.stream().filter(n -> n instanceof EdgeNode).map(n -> (EdgeNode) n).collect(Collectors.toList()));
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

    public void compile() {
        final String fileName = this.target;
        final String encoding = "UTF-8";
        final String START = "digraph G {" + System.lineSeparator();
        final String END = "}";
        try {
            File file = new File(fileName);
            PrintWriter writer = OutputWriter.getInstance(file, encoding).getWriter();
            writer.println(START);
            for (int i = 0; i < this.shapeChildren.size(); i++){
                this.shapeChildren.get(i).compile();
            }
            for (int i = 0; i < this.edgeChildren.size(); i++){
                this.edgeChildren.get(i).compile();
            }
            writer.println(END);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new TransformationException(String.format("File not found: [%s]", fileName), e);
        } catch (UnsupportedEncodingException e) {
            throw new TransformationException(String.format("Unsuported enconding: [%s]", encoding), e);
        }
    }

    public DigraphNode root() {
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
