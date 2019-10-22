package cs.ubc.ca.dsl;

import cs.ubc.ca.errors.ParseException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface IProgram {

    ProgramOutput parse() ;

    ProgramOutput compile();

    String getSource();

    default String getTarget() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Path filepath = Paths.get(classLoader.getResource(this.getSource()).toURI());
            String[] strings = filepath.toString().split("/");
            StringBuilder out = new StringBuilder();
            for (String partialPath : strings) {
                if ("".equals(partialPath)) {
                    continue;
                }
                out.append("/");
                out.append(partialPath);
                if (partialPath.equals("tiny-dot")) {
                    break;
                }
            }

            String fileName = String.format("target_%s", strings[strings.length - 1]);
            return String.format("%s/src/main/resources/build/%s", out.toString(), fileName).replace(".tdot", ".dot");
        } catch (URISyntaxException | NullPointerException e) {
            throw new ParseException(String.format("Unable to find a source file that can be compiled to a target: %s", this.getSource()), e);
        }
    }
}
