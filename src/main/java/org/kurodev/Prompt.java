package org.kurodev;

import org.kurodev.choice.ChoiceOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Prompt {
    private final String id;
    private final String text;
    private final Map<String, String> args;
    private final List<ChoiceOption> options;
    private final List<String> lines = new ArrayList<>();

    public Prompt(String id, String text, Map<String, String> args, List<ChoiceOption> choiceOptions) {
        this.id = id;
        this.text = text;
        this.args = args;
        options = Collections.unmodifiableList(choiceOptions);
    }

    public String getId() {
        return id;
    }

    public String getText() {
        var out = text;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            out = out.replaceAll("\\{\\s*" + entry.getKey() + "\\s*\\}", entry.getValue());
        }
        return out.trim();
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
