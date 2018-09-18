package cs.ubc.ca.parser;

import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.ui.DotProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;


public class ParserTest {

    private DotProgram dotProgram;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void parseValidInput() {
        this.dotProgram = new DotProgram("valid/input.tdot");
        this.dotProgram.parse();

        assertNotNull(this.dotProgram.getAst());
    }

    @Test
    public void parseNonExistingInput() {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Unable to load source: input.tdot");
        this.dotProgram = new DotProgram("input.tdot");
        this.dotProgram.parse();
    }

    @Test
    public void parseInvalidShape() {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Invalid token. Parser was expecting: [circle|square] and received: [universe] instead");
        this.dotProgram = new DotProgram("invalid/non.valid.shape.tdot");
        this.dotProgram.parse();
    }

    @Test
    public void parseIncompleteShapeMissingShape() {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Invalid token. Parser was expecting: [circle|square] and received: [called] instead");
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.shape.tdot");
        this.dotProgram.parse();
    }

    // FIXME: this one is a little tricky because please matches the regex of an identifier. Ideally, I should have a rule that an identifier can't match any of the symbols in my language. As a lazy person, I will leave that to the students.
    @Test
    public void parseIncompleteShapeMissingIdentifier() {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Invalid token. Parser was expecting: [please] and received: [make] instead");
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.identifier.tdot");
        this.dotProgram.parse();
    }


    @Test
    public void parseIncompleteShapeMissingPlease() {
        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Invalid token. Parser was expecting: [please] and received: [make] instead");
        this.dotProgram = new DotProgram("invalid/incomplete.shape.missing.identifier.tdot");
        this.dotProgram.parse();
    }


}