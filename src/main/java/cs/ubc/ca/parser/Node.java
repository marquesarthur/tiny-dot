package cs.ubc.ca.parser;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    static protected PrintWriter writer;

    protected List<Node> children;


    public Node() {
        this.children = new ArrayList<>();
    }

    public Node root() {
        return this;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    boolean hasChildren() {
        return this.children.isEmpty();
    }

    abstract public void parse(Tokenizer context);

    abstract public void compile();
}
