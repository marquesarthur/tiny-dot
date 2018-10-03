package cs.ubc.ca.dsl;

import cs.ubc.ca.analysis.MissingDeclarationListener;
import cs.ubc.ca.analysis.RedeclarationListener;
import cs.ubc.ca.ast.AstVisitor;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import cs.ubc.ca.parser.Tokenizer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        visitor.addListener(this.symbols);
        visitor.traverse();
    }

    public void compile() {
        this.parse();
        AstVisitor visitor = new AstVisitor(this.ast);
        MissingDeclarationListener missingDeclarationListener = new MissingDeclarationListener(this.symbols);
        RedeclarationListener redeclarationListener = new RedeclarationListener(this.symbols);

        visitor.addListener(missingDeclarationListener);
        visitor.addListener(redeclarationListener);
        visitor.traverse();

        this.ast.setTarget(this.getTarget());
        this.ast.compile();
    }

    public Node getAst() {
        return this.ast;
    }

    public SymbolTable getSymbols() {
        return this.symbols;
    }

    public String getTarget() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Path filepath = Paths.get(classLoader.getResource(this.source).toURI());
            String[] strings = filepath.toString().split("/");
            String out = "";
            for (String partialPath : strings) {
                if ("".equals(partialPath)) {
                    continue;
                }
                out += "/";
                out += partialPath;
                if (partialPath.equals("tiny-dot")) {
                    break;
                }
            }

            String fileName = String.format("target_%s", strings[strings.length - 1]);
            return String.format("%s/src/main/resources/build/%s", out, fileName).replace(".tdot", ".dot");
        } catch (URISyntaxException | NullPointerException e) {
            throw new ParseException(String.format("Unable to find a source file that can be compiled to a target: %s", this.source), e);
        }
    }
}
