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

    @Override
    public void parse(Tokenizer context) {
        // TODO: method stub
    }

    @Override
    public void compile() {
        // TODO: method stub
    }
}
