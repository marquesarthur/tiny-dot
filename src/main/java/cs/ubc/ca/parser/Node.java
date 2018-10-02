package cs.ubc.ca.parser;


import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    static protected PrintWriter writer;

    protected List<Node> children;

    protected String target;


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

    public void setTarget(String target) {
        this.target = target;
    }

    abstract public void parse(Tokenizer context);

    abstract public void compile();
}
