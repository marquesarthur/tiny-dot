package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class RedeclarationListener implements PropertyChangeListener, ICompalible {

    private final SymbolTable symbols;

    private List<CompileError> errors;

    public RedeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
        this.errors = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) evt.getNewValue();
            String name = shapeNode.getShape().getName();

            if (this.symbols.contains(name) && !this.symbols.get(name).equals(shapeNode)) {
                CompileError error = new CompileError(String.format("Invalid declaration. Language already contains a shape declared as [%s]", name));
                this.errors.add(error);
            }
        }
    }

    public List<CompileError> getErrors() {
        return errors;
    }
}
