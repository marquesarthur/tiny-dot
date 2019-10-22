package cs.ubc.ca.parser;


import cs.ubc.ca.dsl.OutputWriter;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.errors.TransformationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
public class DigraphNode extends Node {

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
    @Override
    public void parse(Tokenizer context) {
        List<Node> nodes = new ArrayList<>();

        while (context.hasNext()) {
            String nextToken = context.top();
            switch (nextToken) {
                case Tokens.MAKE:
                    ShapeNode shapeNode = new ShapeNode();
                    shapeNode.parse(context);
                    nodes.add(shapeNode);
                    break;
                case Tokens.CONNECT:
                    EdgeNode edgeNode = new EdgeNode();
                    edgeNode.parse(context);
                    nodes.add(edgeNode);
                    break;
                default:
                    throw new ParseException("Unrecognizable token: " + nextToken);
            }
        }

        List<ShapeNode> shapes = nodes.stream().filter(n -> n instanceof ShapeNode).map(n -> (ShapeNode) n).collect(Collectors.toList());
        List<EdgeNode> edges = nodes.stream().filter(n -> n instanceof EdgeNode).map(n -> (EdgeNode) n).collect(Collectors.toList());

        this.children.addAll(shapes);
        this.children.addAll(edges);
    }

    @Override
    public void compile() {
        final String fileName = this.target;
        final String encoding = "UTF-8";
        final String START = "digraph G {" + System.lineSeparator();
        final String END = "}";
        try {
            File file = new File(fileName);
            PrintWriter writer = OutputWriter.getInstance(file, encoding).getWriter();
            writer.println(START);
            children.forEach(Node::compile);
            writer.println(END);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new TransformationException(String.format("File not found: [%s]", fileName), e);
        } catch (UnsupportedEncodingException e) {
            throw new TransformationException(String.format("Unsuported enconding: [%s]", encoding), e);
        }
    }

    public List<ShapeNode> getShapes() {
        return this.children.stream().filter(n -> n instanceof ShapeNode).map(n -> (ShapeNode) n).collect(Collectors.toList());
    }

    public List<EdgeNode> getEdges() {
        return this.children.stream().filter(n -> n instanceof EdgeNode).map(n -> (EdgeNode) n).collect(Collectors.toList());
    }

}
