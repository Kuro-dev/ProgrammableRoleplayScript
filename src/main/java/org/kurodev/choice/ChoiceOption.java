package org.kurodev.choice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoiceOption {
    private final String id;
    private final String text;
    private final Map<String, String> args;
    private final List<ChoicePickedCallback> callbacks = new ArrayList<>();
    private boolean enabled = true;

    public ChoiceOption(String id, String text, Map<String, String> args) {
        this.id = id;
        this.text = text;
        this.args = args;
    }

    @Override
    public String toString() {
        return "$" + id + ':' + text;
    }

    public List<ChoicePickedCallback> getCallbacks() {
        return callbacks;
    }

    public String getId() {
        return id;
    }

    public String getTextRaw() {
        return text;
    }

    public String getText() {
        var out = text;
        out = out.replaceAll(ChoiceParser.INSTRUCTION.pattern(), "").trim();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            out = out.replaceAll("\\{\\s*" + entry.getKey() + "\\s*}", entry.getValue());
        }
        return out.trim();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

