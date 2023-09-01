package org.kurodev;

import org.kurodev.instructions.ScriptFunction;

import java.util.ArrayList;
import java.util.List;

public class ChoiceOption {
    private final List<ScriptFunction> onLoad = new ArrayList<>();
    private final List<ScriptFunction> onClick = new ArrayList<>();
    private final List<ScriptFunction> onUnload = new ArrayList<>();
    private final String id;
    private String text;
    private boolean hidden = false;

    public ChoiceOption(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isVisible() {
        return !hidden;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ScriptFunction> getOnLoad() {
        return onLoad;
    }

    public List<ScriptFunction> getOnClick() {
        return onClick;
    }

    public List<ScriptFunction> getOnUnload() {
        return onUnload;
    }

    public String getId() {
        return id;
    }
}
