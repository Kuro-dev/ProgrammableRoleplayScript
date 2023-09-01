package parsing;

import org.junit.Assert;
import org.junit.Test;
import org.kurodev.parsing.RoleplayScriptParser;
import org.kurodev.parsing.ScriptPreprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ParsingTests {


    //TODO(kuro 04.04.2023): make tests

    private InputStream loadTestFile(String filename) throws IOException {
        return Files.newInputStream(Path.of("./scripts", filename));
    }

    /**
     * This tests the preprocessing of a scripts variables as well as stripping the script of any comments.
     */
    @Test
    public void testPreprocessor() {
        String input = """
                This is a test {var:charName}, do you work?
                I wonder how you.../*/ react to comments...
                """;
        String expected = """
                This is a test Kuro, do you work?
                I wonder how you...
                """.trim();
        Map<String, String> map = new HashMap<>();
        map.put("charName", "Kuro");
        String result = ScriptPreprocessor.run(input, map);
        Assert.assertEquals(expected, result);
    }

    /**
     * This tests the preprocessing of a scripts variables as well as stripping the script of any comments.
     */
    @Test
    public void testScriptParser() {
        RoleplayScriptParser parser = new RoleplayScriptParser(Path.of("./scripts/example1.kcs"));
        parser.getVariables().put("startingLocation", "Varathia");
        parser.parse();
    }
}
