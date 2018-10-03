package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileException;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

public class MissingDeclarationListener implements PropertyChangeListener {

    private final SymbolTable symbols;

    public MissingDeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) evt.getNewValue();

            String from = edgeNode.getEdge().getA();
            String to = edgeNode.getEdge().getB();
            if (!this.symbols.contains(from)) {
                throw new CompileException(String.format("Invalid edge. Language does not contain a shape declared as [%s]", from));
            }

            if (!this.symbols.contains(to)) {
                throw new CompileException(String.format("Invalid edge. Language does not contain a shape declared as [%s]", to));
            }
        }
    }
}
