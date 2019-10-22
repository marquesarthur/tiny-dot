package cs.ubc.ca.dsl;

import cs.ubc.ca.analysis.CircularListener;
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
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class DotProgram implements IProgram {

    private final String source;

    private DigraphNode ast;

    private SymbolTable symbols;

    final static Logger logger = Logger.getLogger(DotProgram.class);

    public DotProgram(String source) {
        this.source = source;
    }

    public ProgramOutput parse() {
        try {
            Tokenizer ctx = new Tokenizer(source);
            DigraphNode parser = new DigraphNode();
            parser.parse(ctx);
            this.ast = parser.root(); //similar until here

            this.symbols = new SymbolTable();

            AstVisitor visitor = new AstVisitor(this.ast);
            visitor.addListener(this.symbols);
            visitor.traverse();
            return new ProgramOutput(0, this.ast, this.symbols, new ArrayList<>());
        } catch (ParseException e) {
            logger.info(e.getMessage());
            return new ProgramOutput(1, this.ast, this.symbols, Collections.singletonList(e));
        }
    }

    public ProgramOutput compile() {
        try {
            ProgramOutput parseOutput = this.parse();
            if (parseOutput.getStatus() == 0){
                parseOutput.getErrors().stream().forEach(e -> logger.info(e.getMessage()));
                return parseOutput;
            }
            AstVisitor visitor = new AstVisitor(this.ast);
//            MissingDeclarationListener missingDeclarationListener = new MissingDeclarationListener(this.symbols);
//            RedeclarationListener redeclarationListener = new RedeclarationListener(this.symbols);
//
//            visitor.addListener(missingDeclarationListener);
//            visitor.addListener(redeclarationListener);

            visitor.traverse();

            this.ast.setTarget(this.getTarget());
            this.ast.compile();

            ProgramOutput output = new ProgramOutput(0, this.ast, this.symbols, new ArrayList<>());
//            this.checkCompileErrors(output, missingDeclarationListener);
//            this.checkCompileErrors(output, redeclarationListener);

            return output;

        } catch (TransformationException | ParseException e) {
            return new ProgramOutput(1, this.ast, this.symbols, Collections.singletonList(e));
        }
    }

    public DigraphNode getAst() {
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
            output.setStatus(1);
            output.getErrors().addAll(compilationAnalysis.getErrors());
        }
    }
}
