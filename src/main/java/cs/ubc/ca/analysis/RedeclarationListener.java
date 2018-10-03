package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileException;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

public class RedeclarationListener implements PropertyChangeListener {

    private final SymbolTable symbols;

    public RedeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) evt.getNewValue();
            String name = shapeNode.getShape().getName();

            if (this.symbols.contains(name) && !this.symbols.get(name).equals(shapeNode)) {
                throw new CompileException(String.format("Invalid declaration. Language already contains a shape declared as [%s]", name));
            }
        }
    }
}
