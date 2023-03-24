package org.kurodev.instructions;

import org.kurodev.KChoices;
import org.kurodev.PromptLoader;
import org.kurodev.choice.ChoiceOption;
import org.kurodev.exceptions.ScriptNotFoundException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public enum Instructions implements RoleplayInstruction {
    NAVIGATE("to") {
        @Override
        public void execute(KChoices game, String argument, ChoiceOption option) {
            option.getCallbacks().add((gameContext, loader, args) -> {
                gameContext.setPrompt(loader.loadPrompt(argument, args));
            });
        }
    },
    LOAD_FILE("load") {
        @Override
        public void execute(KChoices game, String argument, ChoiceOption option) {
            option.getCallbacks().add((gameContext, page, args) -> {
                String fileName;
                String entryPoint;
                if (argument.contains(".")) {
                    final int filenameIndex = 0, entryPointIndex = 1;
                    final var split = argument.split("\\.");
                    fileName = split[filenameIndex];
                    entryPoint = split[entryPointIndex];
                } else {
                    entryPoint = "main";
                    fileName = argument;
                }
                Path scriptPath = game.getPathProvider().apply(fileName);
                if (Files.exists(scriptPath)) {
                    try {
                        String script = Files.readString(scriptPath, StandardCharsets.UTF_8);
                        var newPage = new PromptLoader(script);
                        game.setPage(newPage);
                        game.setPrompt(newPage.loadPrompt(entryPoint, args));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new ScriptNotFoundException(scriptPath);
                }
            });
        }
    },
    CONDITIONAL("if") {
        /**
         *  @param promptChoiceID the id of the choice in the format of promptID.choiceID
         */
        @Override
        public void execute(KChoices game, String promptChoiceID, ChoiceOption option) {
            option.setEnabled(game.getPreviousChoices().contains(promptChoiceID));
        }
    },
    ;

    private final String identifier;

    Instructions(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
