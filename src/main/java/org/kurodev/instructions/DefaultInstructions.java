package org.kurodev.instructions;

import org.kurodev.parsing.ParserContext;

public enum DefaultInstructions implements Instruction {
    GOTO("goto") {
        @Override
        public void apply(ParserContext context, String[] args, String raw) {
            context.getCurrentChoice().getOnClick().add((choiceOption, gameContext) -> {
                if (args.length > 0) {
                    gameContext.loadNextPrompt(args[0]);
                } else {
                    gameContext.throwError("Missing argument in expression: " + raw + ", expected prompt id");
                }
            });

        }
    };

    private final String name;

    DefaultInstructions(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }
}
