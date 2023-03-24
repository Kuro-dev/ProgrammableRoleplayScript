package org.kurodev;

import org.kurodev.choice.ChoiceOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromptLoader {
    private final String script;

    public PromptLoader(String script) {

        this.script = script;
    }

    public List<ChoiceOption> loadChoices(String id) {
        String[] lines = script.split("\n");
        boolean found = false;
        List<ChoiceOption> options = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("[" + id + "]")) {
                found = true;
            } else if (found) {
                if (line.startsWith("["))
                    break;
                int colonIndex = line.indexOf(":");
                if (colonIndex != -1) {
                    String optionId = line.substring(1, colonIndex);
                    String optionText = line.substring(colonIndex + 1).trim();
                    int slashIndex = optionText.indexOf("//");
                    if (slashIndex != -1) {
                        optionText = optionText.substring(0, slashIndex).trim();
                    }
                    options.add(new ChoiceOption(optionId, optionText));
                }
            }
        }
        return options;
    }

    public Prompt loadPrompt(String id, Map<String, String> args) {
        final String idClosingTag = "[$" + id + "]";
        String[] lines = script.split("\n");
        boolean found = false;
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (line.startsWith("[" + id + "]")) {
                found = true;
                if (line.contains(idClosingTag)) {
                    String text = line.substring(id.length() + 2, line.lastIndexOf(idClosingTag));
                    result.append(text.trim());
                    break;
                } else {
                    String text = line.substring(id.length() + 2);
                    result.append(text.trim()).append("\n");
                }
            } else if (found) {
                if (line.contains(idClosingTag)) {
                    String text = line.substring(0, line.lastIndexOf(idClosingTag));
                    result.append(text.trim());
                    break;
                } else {
                    result.append(line.trim()).append("\n");
                }
            }
        }

        return new Prompt(id, result.toString(),args, loadChoices(id));
    }
}

