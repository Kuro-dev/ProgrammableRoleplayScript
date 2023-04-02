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
        assertEquals("this is your option", game.getPrompt().getOptions().get(0).getText());
        assertEquals("option1", game.getPrompt().getOptions().get(0).getId());
    }

    /**
     * # this line should be ignored
     * [main]this is the start point[$main]
     * $option1: this is your option $(to:prompt)
     * $someOtherOption: im just here to distract tbh
     * <p>
     * [prompt]this should be displayed
     * this is a second line for this text[$prompt]
     * $option2: this is an option for the "prompt" text
     * $option3: $(if:main.someOtherOption) this is conditional
     */
    @Test
    public void choicePicking() throws IOException {
        var file = loadTestFile("testScript.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals(2, game.getPrompt().getOptions().size());
        game.pickChoice("option1");
        assertEquals(1, game.getPrompt().getOptions().size());
        assertEquals(1, game.getPreviousChoices().size());
        assertEquals("option2", game.getPrompt().getOptions().get(0).getId());
    }

    /**
     * testScriptLoading.krp:
     * [main] some text[$main]
     * $loadScript: $(load:testScriptToLoad) text//
     * $loadScriptExplicit: $(load:testScriptToLoad.entry) text//
     * <p>
     * testScriptToLoad.krp:
     * <p>
     * [main]display this [$main]
     * <p>
     * [entry]display that[$entry]
     */
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

    /**
     * [main]Hi, my name is { characterName }! what is your name?[$main]
     * $choice1:my name is {playerName}. Pleased to meet you//
     */
    @Test
    public void fileArgumentReplacement() throws IOException {
        var file = loadTestFile("varScript.krp");
        Map<String, String> arguments = new HashMap<>();
        arguments.put("characterName", "Kuro");
        arguments.put("playerName", "Shiro");
        KChoices game = new KChoices(arguments);
        game.start(file);
        assertEquals("Hi, my name is Kuro! what is your name?", game.getPrompt().getText());
        assertEquals("my name is Shiro. Pleased to meet you", game.getPrompt().getOptions().get(0).getText());
    }

    @Test
    public void openingAndClosingTagInSeparateLinesTest() throws IOException {
        var file = loadTestFile("separateTags.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals(0, game.getPrompt().getOptions().size());
        assertEquals("""
                some kind of text
                with multiple lines""", game.getPrompt().getText());
    }
}
