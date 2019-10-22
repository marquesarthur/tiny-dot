package cs.ubc.ca.parser;

import cs.ubc.ca.ast.IListerner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements IListerner {

    private Map<String, ShapeNode> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    public int size() {
        return this.table.size();
    }

    public boolean contains(String name) {
        return this.table.containsKey(name);
    }

    public ShapeNode get(String name) {
        return this.table.get(name);
    }

    public void insert(String name, ShapeNode node) {
        this.table.put(name, node);
    }


    public void visit(Object evt) {
        if (evt instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) evt;
            this.insert(shapeNode.getShape().getName(), shapeNode);
        }
    }
}
