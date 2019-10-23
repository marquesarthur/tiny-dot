package cs.ubc.ca.analysis;

import cs.ubc.ca.ast.IListerner;
import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CircularListener implements IListerner, ICompalible {

    private final SymbolTable symbols;

    private List<CompileError> errors;

    public CircularListener(SymbolTable symbols) {
        this.symbols = symbols;
        this.errors = new ArrayList<>();
    }

    @Override
    public void visit(Object evt) {
        if (evt instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) evt;

            String from = edgeNode.getEdge().getA();
            String to = edgeNode.getEdge().getB();

            if (from.equals(to)) {
                CompileError error = new CompileError(String.format("Invalid edge. LOOP. Language does not contain a shape declared as [%s]", to));
                this.errors.add(error);
            }
        }
    }

    public List<CompileError> getErrors() {
        return errors;
    }
}
