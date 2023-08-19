package org.kurodev;

import java.util.List;

public class Prompt {
    private final String text;
    private final List<ChoiceOption> choices;

    public Prompt(String text, List<ChoiceOption> choices) {
        this.text = text;
        this.choices = choices;
    }
}
