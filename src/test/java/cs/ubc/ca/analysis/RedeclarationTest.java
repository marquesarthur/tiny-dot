package cs.ubc.ca.analysis;

import cs.ubc.ca.errors.CompileException;
import cs.ubc.ca.errors.ParseException;
import cs.ubc.ca.ui.DotProgram;
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
        this.dotProgram.parse();

        assertNotNull(this.dotProgram.getAst());
    }

    @Test
    public void analyzeRedeclaringCircle() {
        expectedEx.expect(CompileException.class);
//        expectedEx.expectMessage("Unable to load source: input.tdot");
        this.dotProgram = new DotProgram("invalid/redeclaration.circle.tdot");
        this.dotProgram.compile();
    }

    @Test
    public void analyzeRedeclaringSquare() {
        expectedEx.expect(CompileException.class);
//        expectedEx.expectMessage("Unable to load source: input.tdot");
        this.dotProgram = new DotProgram("invalid/redeclaration.square.tdot");
        this.dotProgram.compile();
    }
}