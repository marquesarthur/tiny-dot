package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileException;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.util.Observable;
import java.util.Observer;

public class MissingDeclarationListener implements Observer {

    private final SymbolTable symbols;

    public MissingDeclarationListener(SymbolTable symbols) {
        this.symbols = symbols;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) arg;

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
