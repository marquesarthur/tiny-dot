package cs.ubc.ca.ui;

import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import cs.ubc.ca.parser.Tokenizer;

public class DotProgram {

    private final String source;
    private Node parser;
    private Node ast;

    public DotProgram(String source) {
        this.source = source;
    }

    public void parse() {
        Tokenizer ctx = new Tokenizer(source);
        this.parser = new DigraphNode();
        this.parser.parse(ctx);
        this.ast = this.parser.root();
    }


    public void compile() {
        SymbolTable symbols = SymbolTable.getSymbolTable(this.ast);

        this.ast.compile();
    }

    public Node getAst() {
        return this.ast;
    }
}
