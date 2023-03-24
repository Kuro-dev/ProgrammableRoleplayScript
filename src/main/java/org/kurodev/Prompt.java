package org.kurodev;

import org.kurodev.choice.ChoiceOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Prompt {
    private final String id;
    private final String text;
    private final List<ChoiceOption> options;
    private final List<String> lines = new ArrayList<>();

    public Prompt(String id, String text, List<ChoiceOption> choiceOptions) {
        this.id = id;
        this.text = text;
        options = Collections.unmodifiableList(choiceOptions);
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "[" + id + ']' + text;
    }

    public List<ChoiceOption> getOptions() {
        return options.stream().filter(ChoiceOption::isEnabled).collect(Collectors.toList());
    }

    public List<ChoiceOption> getAllOptions() {
        return options;
    }

    public List<String> getLines() {
        return lines;
    }

}
