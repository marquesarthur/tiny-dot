package cs.ubc.ca.analysis;

import com.google.common.collect.Iterables;
import cs.ubc.ca.dsl.ProgramOutput;
import cs.ubc.ca.dsl.ProgramOutputStatus;
import cs.ubc.ca.dsl.DotProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MissingDeclarationTest {

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
    public void analyzeValidInput() {
        this.dotProgram = new DotProgram("valid/sample.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(output.getStatus(), ProgramOutputStatus.SUCCESS);
    }

    @Test
    public void analyzeMissingCircle() {
        this.dotProgram = new DotProgram("invalid/missing.circle.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid edge. Language does not contain a shape declared as [Foo]");
    }

    @Test
    public void analyzeMissingSquare() {
        this.dotProgram = new DotProgram("invalid/missing.square.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(output.getStatus(), ProgramOutputStatus.ERROR);
        assertErrors(output, "Invalid edge. Language does not contain a shape declared as [Bar]");
    }
}