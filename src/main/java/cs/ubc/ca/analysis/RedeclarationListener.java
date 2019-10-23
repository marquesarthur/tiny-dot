package cs.ubc.ca.analysis;

import cs.ubc.ca.ast.IListerner;
import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.ShapeNode;
import cs.ubc.ca.parser.SymbolTable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class RedeclarationListener implements  ICompalible {

    private List<CompileError> errors;



    private final DigraphNode root;



    private List<IListerner> listeners;

    public RedeclarationListener(DigraphNode node) {
        super();
        this.root = node;
        this.errors = new ArrayList<>();
    }

    public void traverse() {
        this.visit();
    }


    public void visit() {
        for (ShapeNode s: this.root.shapeChildren){
            String name = s.getShape().getName();

            if (SymbolTable.contains(name) && !SymbolTable.get(name).equals(s)) {
                CompileError error = new CompileError(String.format("Invalid declaration. Language already contains a shape declared as [%s]", name));
                this.errors.add(error);
            }
        }


    }

    public List<CompileError> getErrors() {
        return errors;
    }
}
