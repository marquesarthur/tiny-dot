package cs.ubc.ca.parser;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {


    private Map<String, Node> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    public static SymbolTable getSymbolTable(Node node) {
        return new SymbolTableVisitor(node).generate();
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
//        TODO: should this be here or somewhere elese?
//        if (this.contains(name)) {
//            throw new SymbolException(String.format("Variable was already declared: %s", name));
//        }
        this.table.put(name, node);
    }

}
