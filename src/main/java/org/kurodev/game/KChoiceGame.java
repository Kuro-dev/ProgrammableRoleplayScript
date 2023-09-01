package org.kurodev.game;

import org.kurodev.ChoiceOption;
import org.kurodev.Prompt;

public class KChoiceGame {

    private final GameContext context;

    public KChoiceGame(GameContext context) {
        this.context = context;
    }

    public void choose(String choiceID) {
        var choice = context.getCurrentPrompt().getChoices().stream().filter(choiceOption -> choiceID.equals(choiceOption.getId())).findFirst();
        choice.ifPresentOrElse(this::choose, () -> context.throwError("Choice with id '" + choiceID + "' does not exist."));
    }

    public void choose(ChoiceOption choice) {
        choice.getOnClick().forEach(scriptFunction -> scriptFunction.accept(choice, context));
    }

    public Prompt getPrompt() {
        return context.getCurrentPrompt();
    }
}
