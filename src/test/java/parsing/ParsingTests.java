package parsing;

import org.junit.Test;
import org.kurodev.KChoices;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ParsingTests {

    private InputStream loadTestFile(String filename) throws IOException {
        return Files.newInputStream(Path.of("./scripts", filename));
    }

    @Test
    public void ignoreComments() throws IOException {
        var file = loadTestFile("testScript.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals("this is the start point", game.getPrompt().getText());
        assertEquals(2, game.getPrompt().getOptions().size());
        assertEquals("this is your option", game.getPrompt().getOptions().get(0).getTextSanitized());
        assertEquals("option1", game.getPrompt().getOptions().get(0).getId());
    }

    @Test
    public void choicePicking() throws IOException {
        var file = loadTestFile("testScript.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals(2, game.getPrompt().getOptions().size());
        game.pickChoice(game.getPrompt().getOptions().get(0).getId());
        assertEquals(1, game.getPrompt().getOptions().size());
        assertEquals(1, game.getPreviousChoices().size());
        assertEquals("option2", game.getPrompt().getOptions().get(0).getId());
    }

    @Test
    public void fileLoadingDefaultToMain() throws IOException {
        var file = loadTestFile("testScriptLoading.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals("some text", game.getPrompt().getText());
        assertEquals(2, game.getPrompt().getOptions().size());
        game.pickChoice("loadScript");
        assertEquals("display this", game.getPrompt().getText());
    }

    @Test
    public void fileLoadingToCustomEntryPoint() throws IOException {
        var file = loadTestFile("testScriptLoading.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals("some text", game.getPrompt().getText());
        assertEquals(2, game.getPrompt().getOptions().size());
        game.pickChoice("loadScriptExplicit");
        assertEquals("display that", game.getPrompt().getText());
    }

    @Test
    public void fileArgumentReplacement() throws IOException {
        var file = loadTestFile("varScript.krp");
        Map<String, String> arguments = new HashMap<>();
        arguments.put("characterName", "Kuro");
        KChoices game = new KChoices(arguments);
        game.start(file);
        assertEquals("Hi, my name is Kuro! what is your name?", game.getPrompt().getText());
    }
}
