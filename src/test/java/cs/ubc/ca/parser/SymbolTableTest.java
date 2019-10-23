package cs.ubc.ca.parser;


import cs.ubc.ca.dsl.DotProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class SymbolTableTest {

    private DotProgram dotProgram;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void parseValidInput() {
        this.dotProgram = new DotProgram("valid/sample.tdot");
        this.dotProgram.parse();
        assertNotNull(this.dotProgram.getSymbols());
        assertEquals(2, SymbolTable.size());
        assertTrue(SymbolTable.contains("Fido"));
        assertTrue(SymbolTable.contains("Biff"));
        assertThat(SymbolTable.get("Fido"), instanceOf(ShapeNode.class));
        assertThat(SymbolTable.get("Biff"), instanceOf(ShapeNode.class));
    }
}