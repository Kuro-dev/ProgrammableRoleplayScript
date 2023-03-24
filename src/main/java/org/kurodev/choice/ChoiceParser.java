package org.kurodev.choice;

import org.kurodev.KChoices;
import org.kurodev.PromptLoader;
import org.kurodev.instructions.Instructions;
import org.kurodev.instructions.RoleplayInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ChoiceParser {
    public static final Pattern INSTRUCTION = Pattern.compile("(\\$\\((.+)\\))");
    private static final int INSTRUCTION_TYPE_INDEX = 0;
    private static final int INSTRUCTION_ARG_INDEX = 1;
    public static List<RoleplayInstruction> INSTRUCTION_SET = new ArrayList<>();

    static {
        INSTRUCTION_SET.addAll(Arrays.asList(Instructions.values()));
    }

    private final ChoiceOption option;

    public ChoiceParser(ChoiceOption option) {

        this.option = option;
    }

    public void parse(KChoices context, PromptLoader page) {
        var matcher = INSTRUCTION.matcher(option.getText());
        while (matcher.find()) {
            String instruction = matcher.group(2);
            String[] instructions = instruction.split(":");
            for (RoleplayInstruction value : INSTRUCTION_SET) {
                if (value.getIdentifier().trim().equalsIgnoreCase(instructions[INSTRUCTION_TYPE_INDEX])) {
                    value.execute(context, instructions[INSTRUCTION_ARG_INDEX].trim(), option);
                    break;
                }
            }
        }
    }
}
