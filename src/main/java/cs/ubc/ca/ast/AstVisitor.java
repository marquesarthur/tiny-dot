package cs.ubc.ca.ast;

import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.ShapeNode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

// see: https://www.baeldung.com/java-observer-pattern
public class AstVisitor {

    private final DigraphNode root;


    private List<IListerner> listeners;

    public AstVisitor(DigraphNode node) {
        super();
        this.root = node;
    }

    public void traverse() {
        this.visit();
    }

    public void addListener(IListerner lst){
        if (this.listeners == null){
            this.listeners = new ArrayList<>();
        }
        this.listeners.add(lst);

    }

    public void visit() {
        for (IListerner lst: this.listeners) {
            for (ShapeNode s: this.root.shapeChildren){
                lst.visit(s);
            }

            for (EdgeNode s: this.root.edgeChildren){
                lst.visit(s);
            }
        }

    }


}