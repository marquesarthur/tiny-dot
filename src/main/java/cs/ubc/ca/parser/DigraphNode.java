package cs.ubc.ca.parser;


import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.errors.TransformationException;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DigraphNode extends Node {

    private static final String START = "digraph G {" + System.lineSeparator();
    private static final String END = "}";

    public DigraphNode() {
        super();
    }

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
        try {
            File file = new File(fileName);

            writer = new PrintWriter(file, encoding);
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
