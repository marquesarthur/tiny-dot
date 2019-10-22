package cs.ubc.ca.dsl;

import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;

import java.util.List;

public class ProgramOutput {

    private int status;

    private SymbolTable symbolTable;

    private DigraphNode ast;

    private List<RuntimeException> errors;


    public ProgramOutput(int status, DigraphNode ast, SymbolTable symbolTable, List<RuntimeException> errors) {
        this.status = status;
        this.ast = ast;
        this.symbolTable = symbolTable;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public DigraphNode getAst() {
        return ast;
    }

    public void setAst(DigraphNode ast) {
        this.ast = ast;
    }

    public List<RuntimeException> getErrors() {
        return errors;
    }

    public void setErrors(List<RuntimeException> errors) {
        this.errors = errors;
    }
}
