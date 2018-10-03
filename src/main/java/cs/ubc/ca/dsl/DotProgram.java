package cs.ubc.ca.dsl;

import cs.ubc.ca.analysis.ICompalible;
import cs.ubc.ca.analysis.MissingDeclarationListener;
import cs.ubc.ca.analysis.RedeclarationListener;
import cs.ubc.ca.ast.AstVisitor;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.errors.TransformationException;
import cs.ubc.ca.parser.DigraphNode;
import cs.ubc.ca.parser.Node;
import cs.ubc.ca.parser.SymbolTable;
import cs.ubc.ca.parser.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;

public class DotProgram implements IProgram {

    private final String source;

    private Node parser;

    private Node ast;

    private SymbolTable symbols;

    public DotProgram(String source) {
        this.source = source;
    }

    public ProgramOutput parse() {
        try {
            Tokenizer ctx = new Tokenizer(source);
            this.parser = new DigraphNode();
            this.parser.parse(ctx);
            this.ast = this.parser.root();

            this.symbols = new SymbolTable();

            AstVisitor visitor = new AstVisitor(this.ast);
            visitor.addListener(this.symbols);
            visitor.traverse();
            return new ProgramOutput(ProgramOutputStatus.SUCCESS, this.ast, this.symbols, new ArrayList<>());
        } catch (ParseException e) {
            return new ProgramOutput(ProgramOutputStatus.ERROR, this.ast, this.symbols, Collections.singletonList(e));
        }
    }

    public ProgramOutput compile() {
        try {
            this.parse();
            AstVisitor visitor = new AstVisitor(this.ast);
            MissingDeclarationListener missingDeclarationListener = new MissingDeclarationListener(this.symbols);
            RedeclarationListener redeclarationListener = new RedeclarationListener(this.symbols);

            visitor.addListener(missingDeclarationListener);
            visitor.addListener(redeclarationListener);
            visitor.traverse();

            this.ast.setTarget(this.getTarget());
            this.ast.compile();

            ProgramOutput output = new ProgramOutput(ProgramOutputStatus.SUCCESS, this.ast, this.symbols, new ArrayList<>());
            this.checkCompileErrors(output, missingDeclarationListener);
            this.checkCompileErrors(output, redeclarationListener);

            return output;

        } catch (TransformationException | ParseException e) {
            return new ProgramOutput(ProgramOutputStatus.ERROR, this.ast, this.symbols, Collections.singletonList(e));
        }
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

    private void checkCompileErrors(ProgramOutput output, ICompalible compilationAnalysis) {
        if (!compilationAnalysis.getErrors().isEmpty()) {
            output.setStatus(ProgramOutputStatus.ERROR);
            output.getErrors().addAll(compilationAnalysis.getErrors());
        }
    }
}
