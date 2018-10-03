package cs.ubc.ca.dsl;

import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;

import java.util.List;

public class ProgramOutput {

    private ProgramOutputStatus status;

    private SymbolTable symbolTable;

    private Node ast;

    private List<RuntimeException> errors;


    public ProgramOutput(ProgramOutputStatus status, Node ast, SymbolTable symbolTable, List<RuntimeException> errors) {
        this.status = status;
        this.ast = ast;
        this.symbolTable = symbolTable;
        this.errors = errors;
    }

    public ProgramOutputStatus getStatus() {
        return status;
    }

    public void setStatus(ProgramOutputStatus status) {
        this.status = status;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Node getAst() {
        return ast;
    }

    public void setAst(Node ast) {
        this.ast = ast;
    }

    public List<RuntimeException> getErrors() {
        return errors;
    }

    public void setErrors(List<RuntimeException> errors) {
        this.errors = errors;
    }
}
