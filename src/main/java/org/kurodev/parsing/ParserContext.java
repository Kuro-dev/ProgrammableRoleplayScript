package org.kurodev.parsing;

import org.kurodev.ChoiceOption;
import org.kurodev.Prompt;

public class ParserContext {
    private ChoiceOption currentChoice = null;
    private Prompt currentPrompt = null;

    public Prompt getCurrentPrompt() {
        return currentPrompt;
    }

    public void setCurrentPrompt(Prompt currentPrompt) {
        this.currentPrompt = currentPrompt;
    }

    public ChoiceOption getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(ChoiceOption currentChoice) {
        this.currentChoice = currentChoice;
    }
}
