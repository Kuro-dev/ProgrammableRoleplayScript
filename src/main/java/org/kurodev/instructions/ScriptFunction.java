package org.kurodev.instructions;

import org.kurodev.ChoiceOption;
import org.kurodev.game.GameContext;

import java.util.function.BiConsumer;

public interface ScriptFunction extends BiConsumer<ChoiceOption, GameContext> {
}
