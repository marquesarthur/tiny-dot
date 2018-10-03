package cs.ubc.ca.dsl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DotProgramTest {

    private DotProgram dotProgram;


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
    }

    private void assertGeneratedOutputMatches(String outputPath, String expectedPath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Path filepath = Paths.get(classLoader.getResource(expectedPath).toURI());
            String expectedProgram = new String(Files.readAllBytes(filepath), StandardCharsets.UTF_8);
            String outputProgram = new String(Files.readAllBytes(Paths.get(outputPath)), StandardCharsets.UTF_8);
            assertEquals(expectedProgram, outputProgram);
        } catch (URISyntaxException | IOException e) {
            fail();
        }
    }

    private void assertCompilation(String source) {
        this.dotProgram = new DotProgram(source);
        ProgramOutput output = this.dotProgram.compile();
        assertEquals(output.getStatus(), ProgramOutputStatus.SUCCESS);
    }

    @Test
    public void testSample() {
        assertCompilation("valid/sample.tdot");
        assertGeneratedOutputMatches(this.dotProgram.getTarget(),"valid/output/sample.dot");
    }

    @Test
    public void testFidoBiff() {
        assertCompilation("valid/FidoBiff.tdot");
        assertGeneratedOutputMatches(this.dotProgram.getTarget(), "valid/output/FidoBiff.dot");
    }

    @Test
    public void testFooBar() {
        assertCompilation("valid/FooBar.tdot");
        assertGeneratedOutputMatches(this.dotProgram.getTarget(), "valid/output/FooBar.dot");
    }

    @Test
    public void testLifeUniverseAndEverythingElse() {
        assertCompilation("valid/LifeUniverseAndEverythingElse.tdot");
        assertGeneratedOutputMatches("valid/output/LifeUniverseAndEverythingElse.dot", this.dotProgram.getTarget());
    }
}
