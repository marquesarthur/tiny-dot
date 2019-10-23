package cs.ubc.ca.analysis;

import cs.ubc.ca.parser.Node;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// see: https://www.baeldung.com/java-observer-pattern
public class AstVisitor {

    private final Node root;

    private PropertyChangeSupport support;

    private Node lastVisited;

    public AstVisitor(Node node) {
        super();
        this.support = new PropertyChangeSupport(this);
        this.root = node;
    }

    public void traverse() {
        this.visit(this.root);
    }

    public void visit(Node node) {
        // fires to the listener: the event, it's old value and the new value, as we don't keep track of the old value, the second parameter is pointless
        // however, the api requires that old value != new value. Hence, I added this last visited attribute
        this.support.firePropertyChange("node", this.lastVisited, node);
        this.lastVisited = node;
        this.visitChildren(node);
    }

    private void visitChildren(Node node) {
        if (node.getChildren() != null) {
            for (Node child : node.getChildren()) {
                this.visit(child);
            }
        }
    }

    public void addListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener("node", listener);
    }
}