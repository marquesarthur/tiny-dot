package cs.ubc.ca.parser;

import cs.ubc.ca.ast.IListerner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable  {

    public static Map<String, ShapeNode> table = new HashMap<>();
    ;


    public static int size() {
        return SymbolTable.table.size();
    }

    public static boolean contains(String name) {
        return SymbolTable.table.containsKey(name);
    }

    public static ShapeNode get(String name) {
        return SymbolTable.table.get(name);
    }

    public static void insert(String name, ShapeNode node) {
        SymbolTable.table.put(name, node);
    }

    private final DigraphNode root;


    private List<IListerner> listeners;

    public SymbolTable(DigraphNode node) {
        super();
        this.root = node;
    }

    public void traverse() {
        this.visit();
    }


    public void visit() {
            for (ShapeNode s: this.root.shapeChildren){
                SymbolTable.insert(s.getShape().getName(), s);
            }


    }

}
