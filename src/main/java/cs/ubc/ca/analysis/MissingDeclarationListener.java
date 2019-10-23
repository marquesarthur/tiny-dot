package cs.ubc.ca.analysis;

import cs.ubc.ca.ast.IListerner;
import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MissingDeclarationListener implements ICompalible {


    private List<CompileError> errors = new ArrayList<>();


    public List<CompileError> getErrors() {
        return errors;
    }


    private final DigraphNode root;

    public MissingDeclarationListener(DigraphNode node) {
        super();
        this.root = node;
        this.errors = new ArrayList<>();
    }

    public void traverse() {
        this.visit();
    }


    public void visit() {
        for (EdgeNode s : this.root.edgeChildren) {

            String from = s.getEdge().getA();
            String to = s.getEdge().getB();
            if (!SymbolTable.contains(to)) {
                CompileError error = new CompileError(String.format("Invalid edge. Language does not contain a shape declared as [%s]", to));
                this.errors.add(error);
            }

            if (!SymbolTable.contains(from)) {
                CompileError error = new CompileError(String.format("Invalid edge. Language does not contain a shape declared as [%s]", from));
                this.errors.add(error);
            }
        }


    }

}
