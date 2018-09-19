package cs.ubc.ca.ast;

import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.ShapeNode;

import java.util.Collection;
import java.util.Collections;
import java.util.Observable;


public class AstVisitor extends Observable {

    private final Node root;

    public AstVisitor(Node node) {
        super();
        this.root = node;
    }

    public void traverse() {
        this.visit(this.root);
    }

    public void visit(Node node) {
        setChanged();
        notifyObservers(node);
        this.visitChildren(node);
    }

    private void visitChildren(Node node) {
        for (Node child : node.getChildren()) {
            this.visit(child);
        }
    }


}