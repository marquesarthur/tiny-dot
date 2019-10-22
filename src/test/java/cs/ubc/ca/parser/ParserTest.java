package cs.ubc.ca.parser;

import com.google.common.collect.Iterables;
import cs.ubc.ca.dsl.DotProgram;
import cs.ubc.ca.dsl.ProgramOutput;
import cs.ubc.ca.dsl.ProgramOutputStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ParserTest {

    private DotProgram dotProgram;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
    }

    private void assertErrors(String expectedMsg, ProgramOutput output) {
        RuntimeException error = Iterables.getFirst(output.getErrors(), null);
        assertNotNull(error);
        assertEquals(expectedMsg, error.getMessage());
    }

    @Test
    public void parseValidInput() {
        this.dotProgram = new DotProgram("valid/sample.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.SUCCESS, output.getStatus());
        assertNotNull(this.dotProgram.getAst());
    }

    @Test
    public void parseValidSimpleInput() {
        this.dotProgram = new DotProgram("valid/simple.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.SUCCESS, output.getStatus());
        assertNotNull(this.dotProgram.getAst());
    }

    @Test
    public void parseNonExistingInput() {
        this.dotProgram = new DotProgram("sample.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.ERROR, output.getStatus());
        assertErrors("Unable to load source: sample.tdot", output);
    }

    @Test
    public void parseInvalidShape() {
        this.dotProgram = new DotProgram("invalid/non.valid.shape.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.ERROR, output.getStatus());
        assertErrors("Invalid token at line 1.\nParser was expecting: [circle|square] and received: [universe] instead", output);
    }

    @Test
    public void parseIncompleteShapeMissingShape() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.shape.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.ERROR, output.getStatus());
        assertErrors("Invalid token at line 2.\nParser was expecting: [circle|square] and received: [called] instead", output);
    }

    // FIXME: this one is a little tricky because please matches the regex of an identifier. Ideally, I should have a rule that an identifier can't match any of the symbols in my language. As a lazy person, I will leave that to the students.
    @Test
    public void parseIncompleteShapeMissingIdentifier() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.identifier.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.ERROR, output.getStatus());
        assertErrors( "Invalid token at line 1.\nParser was expecting: [please] and received: [make] instead", output);
    }

    @Test
    public void parseIncompleteShapeMissingPlease() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.please.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(ProgramOutputStatus.ERROR, output.getStatus());
        assertErrors( "Invalid token at line 2.\nParser was expecting: [please] and received: [connect] instead", output);
    }
}