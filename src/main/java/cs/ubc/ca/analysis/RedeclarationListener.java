package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileException;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.util.Observable;
import java.util.Observer;

public class RedeclarationListener implements Observer {

    private final SymbolTable symbols;

    public RedeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) arg;
            String name = shapeNode.getShape().getName();

            if (this.symbols.contains(name) && !this.symbols.get(name).equals(shapeNode)) {
                throw new CompileException(String.format("Invalid declaration. Language already contains a shape declared as [%s]", name));
            }
        }
    }
}
