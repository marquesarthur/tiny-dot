package cs.ubc.ca.dsl;

import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import org.apache.log4j.Logger;


public class DotProgram implements IProgram {

    private final String source;

    private Node ast;

    private SymbolTable symbols;

    final static Logger logger = Logger.getLogger(DotProgram.class);

    public DotProgram(String source) {
        this.source = source;
    }

    public ProgramOutput parse() {
        return null;
    }

    public ProgramOutput compile() {
        return null;
    }

    public Node getAst() {
        return this.ast;
    }

    public SymbolTable getSymbols() {
        return this.symbols;
    }

    public String getSource() {
        return this.source;
    }
}
