package org.kurodev;

import java.util.Collections;
import java.util.List;

public class Prompt {
    private final String text;
    private final List<ChoiceOption> choices;

    public Prompt(String text, List<ChoiceOption> choices) {
        this.text = text;
        this.choices = choices;
    }

    public String getText() {
        return text;
    }

    public List<ChoiceOption> getAllChoices() {
        return Collections.unmodifiableList(choices);
    }

    public List<ChoiceOption> getChoices() {
        return choices.stream().filter(ChoiceOption::isVisible).toList();
    }
}
