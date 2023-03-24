package org.kurodev.choice;

import java.util.ArrayList;
import java.util.List;

public class ChoiceOption {
    private final String id;
    private final String text;
    private final List<ChoicePickedCallback> callbacks = new ArrayList<>();
    private boolean enabled = true;

    public ChoiceOption(String id, String text) {
        this.id = id;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public String getTextSanitized() {
        return text.replaceAll(ChoiceParser.INSTRUCTION.pattern(), "").trim();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

