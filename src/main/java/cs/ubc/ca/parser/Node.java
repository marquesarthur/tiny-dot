package cs.ubc.ca.parser;


import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that represents a Node in the AST representation of a DSL.
 *
 * Classes that inherit from Node must provide a list of terminals and non-terminals that are interpreted at parse time.
 *
 * At compile time, child classes convert their respective data structures to a digraph format and write that to the defined output writer.
 *
 * @author Arthur Marques
 */
public abstract class Node {

    protected String target;

    protected List<Node> children;

    abstract public void compile();

    /**
     * Parses the content of a tokenizer generating the current node as well as child nodes
     *
     * @param context
     */
    abstract public void parse(Tokenizer context);

    public Node() {
        this.children = new ArrayList<>();
    }

    public Node root() {
        return this;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    /**
     * Method to generate the .dot program output at a target path location
     *
     * @return the path for the target program
     */
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

}
