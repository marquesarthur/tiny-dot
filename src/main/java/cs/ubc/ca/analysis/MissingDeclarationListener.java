package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MissingDeclarationListener implements PropertyChangeListener {

    private final SymbolTable symbols;

    private List<CompileError> errors;

    public MissingDeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
        this.errors = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) evt.getNewValue();

            String from = edgeNode.getEdge().getA();
            String to = edgeNode.getEdge().getB();
            if (!this.symbols.contains(from)) {
                CompileError error = new CompileError(String.format("Invalid edge. Language does not contain a shape declared as [%s]", from));
                this.errors.add(error);
            }

            if (!this.symbols.contains(to)) {
                CompileError error = new CompileError(String.format("Invalid edge. Language does not contain a shape declared as [%s]", to));
                this.errors.add(error);
            }
        }
    }
}
