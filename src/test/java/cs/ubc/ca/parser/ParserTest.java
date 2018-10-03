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

    private void assertErrors(ProgramOutput output, String expectedMsg) {
        RuntimeException error = Iterables.getFirst(output.getErrors(), null);
        assertNotNull(error);
        assertEquals(error.getMessage(), expectedMsg);
    }

    @Test
    public void parseValidInput() {
        this.dotProgram = new DotProgram("valid/sample.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.SUCCESS);
        assertNotNull(this.dotProgram.getAst());
    }

    @Test
    public void parseNonExistingInput() {
        this.dotProgram = new DotProgram("sample.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Unable to load source: sample.tdot");
    }

    @Test
    public void parseInvalidShape() {
        this.dotProgram = new DotProgram("invalid/non.valid.shape.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid token. Parser was expecting: [circle|square] and received: [universe] instead");
    }

    @Test
    public void parseIncompleteShapeMissingShape() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.shape.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid token. Parser was expecting: [circle|square] and received: [called] instead");
    }

    // FIXME: this one is a little tricky because please matches the regex of an identifier. Ideally, I should have a rule that an identifier can't match any of the symbols in my language. As a lazy person, I will leave that to the students.
    @Test
    public void parseIncompleteShapeMissingIdentifier() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.identifier.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid token. Parser was expecting: [please] and received: [make] instead");
    }

    @Test
    public void parseIncompleteShapeMissingPlease() {
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.identifier.tdot");
        ProgramOutput output = this.dotProgram.parse();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid token. Parser was expecting: [please] and received: [make] instead");
    }
}