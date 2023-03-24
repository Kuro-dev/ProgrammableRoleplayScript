package parsing;

import org.junit.Test;
import org.kurodev.KChoices;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ParsingTests {

    private InputStream loadTestFile(String filename) {
        return this.getClass().getResourceAsStream("/testfiles/" + filename);
    }

    @Test
    public void ignoreComments() {
        var file = loadTestFile("testScript.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals("this is the start point", game.getPrompt().getText());
        assertEquals(2, game.getPrompt().getOptions().size());
        assertEquals("this is your option", game.getPrompt().getOptions().get(0).getTextSanitized());
        assertEquals("option1", game.getPrompt().getOptions().get(0).getId());
    }

    @Test
    public void choicePicking() {
        var file = loadTestFile("testScript.krp");
        KChoices game = new KChoices();
        game.start(file);
        assertEquals(2, game.getPrompt().getOptions().size());
        game.pickChoice(game.getPrompt().getOptions().get(0).getId());
        assertEquals(1, game.getPrompt().getOptions().size());
        assertEquals(1, game.getPreviousChoices().size());
        assertEquals("option2", game.getPrompt().getOptions().get(0).getId());
    }
}
