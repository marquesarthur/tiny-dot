package cs.ubc.ca.dsl;

import cs.ubc.ca.analysis.MissingDeclarationListener;
import cs.ubc.ca.analysis.RedeclarationListener;
import cs.ubc.ca.ast.AstVisitor;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import cs.ubc.ca.parser.Tokenizer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
        this.parse();
        AstVisitor visitor = new AstVisitor(this.ast);
        MissingDeclarationListener missingDeclarationListener = new MissingDeclarationListener(this.symbols);
        RedeclarationListener redeclarationListener = new RedeclarationListener(this.symbols);

        visitor.addObserver(missingDeclarationListener);
        visitor.addObserver(redeclarationListener);
        visitor.traverse();

        this.ast.setTarget(this.getTarget());
        this.ast.compile();
    }

    public String getTarget() {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            URI uri = classLoader.getResource(this.source).toURI();
            uri.getPath();
            String[] path = this.source.split("/");
            String fileName = path[path.length - 1];

            URI filePath = getClass().getResource(String.format("build/%s", fileName)).toURI();
            return filePath.getPath();
        } catch (URISyntaxException | NullPointerException e) {
            throw new ParseException(String.format("Unable to find a source file that can be compiled to a target: %s", this.source), e);
        }
    }


    public Node getAst() {
        return this.ast;
    }

    public SymbolTable getSymbols() {
        return this.symbols;
    }
}
