package org.kurodev.game;

import org.kurodev.ChoiceOption;
import org.kurodev.Prompt;
import org.kurodev.parsing.Errorhandler;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class GameContext {
    private final Map<String, String> variables;
    private final Errorhandler errorhandler;
    private Path file;
    private Map<String, Prompt> promptMap = new HashMap<>();
    private Prompt currentPrompt;

    public GameContext(Path file, Map<String, String> variables, Errorhandler errorhandler) {
        this.variables = variables;
        this.errorhandler = errorhandler;
        this.file = file;
    }

    public Prompt getCurrentPrompt() {
        return currentPrompt;
    }

    public void setCurrentPrompt(Prompt currentPrompt) {
        if (currentPrompt != null)
            for (ChoiceOption choice : currentPrompt.getChoices()) {
                choice.getOnUnload().forEach(scriptFunction -> scriptFunction.accept(choice, this));
            }
        this.currentPrompt = currentPrompt;
        for (ChoiceOption choice : currentPrompt.getChoices()) {
            choice.getOnLoad().forEach(scriptFunction -> scriptFunction.accept(choice, this));
        }
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void throwError(String msg) {
        errorhandler.onError(msg, file);
    }

    public void loadNextPrompt(String promptId) {
        if (promptId.contains(".")) {
            //load file
        } else {
            setCurrentPrompt(promptMap.get(promptId));
        }
    }
}
