package cs.ubc.ca.parser;

import java.util.*;

public class SymbolTable implements Observer {

    private Map<String, Node> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    public int size() {
        return this.table.size();
    }

    public boolean contains(String name) {
        return this.table.containsKey(name);
    }

    public Node get(String name) {
        return this.table.get(name);
    }

    public void insert(String name, Node node) {
        this.table.put(name, node);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) arg;
            this.insert(shapeNode.getShape().getName(), shapeNode);
        }
    }
}
