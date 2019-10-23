package cs.ubc.ca.analysis;

import com.google.common.collect.Iterables;
import cs.ubc.ca.dsl.DotProgram;
import cs.ubc.ca.dsl.ProgramOutput;
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


    @Test
    public void analyzeValidInput() {
        this.dotProgram = new DotProgram("valid/sample.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(0, output.getStatus());
    }

    @Test
    public void analyzeMissingCircle() {
        this.dotProgram = new DotProgram("invalid/missing.circle.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(1, output.getStatus());

        RuntimeException error = Iterables.getFirst(output.getErrors(), null);
        assertNotNull(error);
        assertEquals("Invalid edge. Language does not contain a shape declared as [Foo]", error.getMessage());
    }

    @Test
    public void analyzeMissingSquare() {
        this.dotProgram = new DotProgram("invalid/missing.square.tdot");
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(1, output.getStatus());

        RuntimeException error = Iterables.getFirst(output.getErrors(), null);
        assertNotNull(error);
        assertEquals("Invalid edge. Language does not contain a shape declared as [Bar]", error.getMessage());
    }
}