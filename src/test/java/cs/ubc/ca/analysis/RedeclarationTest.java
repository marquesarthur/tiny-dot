package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileError;
import cs.ubc.ca.dsl.DotProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;


public class RedeclarationTest {

    private DotProgram dotProgram;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void analyzeValidInput() {
        this.dotProgram = new DotProgram("valid/input.tdot");
        this.dotProgram.compile();
    }

    @Test
    public void analyzeRedeclaringCircle() {
        expectedEx.expect(CompileError.class);
//        expectedEx.expectMessage("Unable to load source: input.tdot");
        this.dotProgram = new DotProgram("invalid/redeclaration.circle.tdot");
        this.dotProgram.compile();
    }

    @Test
    public void analyzeRedeclaringSquare() {
        expectedEx.expect(CompileError.class);
//        expectedEx.expectMessage("Unable to load source: input.tdot");
        this.dotProgram = new DotProgram("invalid/redeclaration.square.tdot");
        this.dotProgram.compile();
    }
}