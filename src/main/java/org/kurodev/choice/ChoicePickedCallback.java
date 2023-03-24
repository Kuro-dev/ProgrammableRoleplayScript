package org.kurodev.choice;

import org.kurodev.KChoices;
import org.kurodev.PromptLoader;

import java.util.Map;

public interface ChoicePickedCallback {
    void onSelected(KChoices gameContext, PromptLoader page, Map<String, String> args);
}
