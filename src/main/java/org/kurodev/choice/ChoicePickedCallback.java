package org.kurodev.choice;

import org.kurodev.KChoices;
import org.kurodev.PromptLoader;

public interface ChoicePickedCallback {
  void  onSelected(KChoices gameContext, PromptLoader page);
}
