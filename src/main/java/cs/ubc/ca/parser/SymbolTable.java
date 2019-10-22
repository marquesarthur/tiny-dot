package cs.ubc.ca.parser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Symbol table that contains all the identifiers declared as a shape in the DSL.
 * Class implements the property change listener design pattern.
 *
 * At traversing time, whenever the current node changes, the {@link SymbolTable#propertyChange}
 * will fire and the node being visited will be added to the symbol table (in case it is a shape node).
 */
public class SymbolTable implements PropertyChangeListener {

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
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) evt.getNewValue();
            this.insert(shapeNode.getShape().getName(), shapeNode);
        }
    }
}
