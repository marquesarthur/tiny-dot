package cs.ubc.ca.ui;

import cs.ubc.ca.ast.AstVisitor;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import cs.ubc.ca.parser.Tokenizer;

public class DotProgram {

    private final String source;

    private Node parser;

    private Node ast;

    private SymbolTable symbols;

    public DotProgram(String source) {
        this.source = source;
    }

    public void parse() {
        Tokenizer ctx = new Tokenizer(source);
        this.parser = new DigraphNode();
        this.parser.parse(ctx);
        this.ast = this.parser.root();

        this.symbols = new SymbolTable();

        AstVisitor visitor = new AstVisitor(this.ast);
        visitor.addObserver(this.symbols);
        visitor.traverse();
    }

    public void compile() {
        this.ast.compile();
    }

    public Node getAst() {
        return this.ast;
    }

    public SymbolTable getSymbols() {
        return this.symbols;
    }
}
