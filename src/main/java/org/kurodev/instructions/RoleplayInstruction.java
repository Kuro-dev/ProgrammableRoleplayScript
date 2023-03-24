package org.kurodev.instructions;

import org.kurodev.KChoices;
import org.kurodev.choice.ChoiceOption;

public interface RoleplayInstruction {
    String getIdentifier();

    void execute(KChoices game, String argument, ChoiceOption option);
}
