package org.kurodev.parsing;

import org.kurodev.ChoiceOption;
import org.kurodev.Prompt;
import org.kurodev.game.KChoiceGame;
import org.kurodev.instructions.DefaultInstructions;
import org.kurodev.instructions.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoleplayScriptParser {
    private static final Logger logger = LoggerFactory.getLogger(RoleplayScriptParser.class);
    private static final Pattern GENERIC_INSTRUCTIONS_PATTERN = Pattern.compile("\\{([\\w:,\\s.]+)\\}");
    private final Map<String, String> variables = new HashMap<>();

    private final Set<Instruction> instructionSet = new HashSet<>();

    private final Path script;
    private final Errorhandler errorhandler;
    private String scriptRaw;

    public RoleplayScriptParser(Path script) {
        this(script, ParserMode.STRICT);
    }

    public RoleplayScriptParser(String script) {
        this(script, ParserMode.STRICT);
    }

    public RoleplayScriptParser(String script, ParserMode mode) {
        this(null, (Errorhandler) mode);
        this.scriptRaw = script;
        instructionSet.addAll(List.of(DefaultInstructions.values()));
    }

    public RoleplayScriptParser(Path script, Errorhandler errorhandler) {
        this.script = script;
        this.errorhandler = errorhandler;
    }

    public Set<Instruction> getInstructionSet() {
        return instructionSet;
    }

    /**
     * @return a game instance starting at the "main" prompt
     */
    public KChoiceGame parse() {
        return parse("main");
    }

    /**
     * @return a game instance starting at the given prompt. Default is "main"
     */
    public KChoiceGame parse(String mainPrompt) {
        if (Objects.isNull(scriptRaw)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(script)))) {
                scriptRaw = reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Objects.requireNonNull(scriptRaw);
        String normalised = ScriptPreprocessor.run(scriptRaw, variables);
        Set<Prompt> prompts = generatePrompts(normalised);
        return null;
    }

    private Set<Prompt> generatePrompts(String normalised) {
        Matcher instructionsMatcher = GENERIC_INSTRUCTIONS_PATTERN.matcher(normalised);
        Set<Prompt> out = new HashSet<>();
        Prompt currentPrompt = null;
        List<ChoiceOption> options = new ArrayList<>();
        ChoiceOption currentChoice = null;
        int lastPrompt = 0;
        ParserContext context = new ParserContext();
        while (instructionsMatcher.find()) {
            Map<String, String[]> instructionsMap = createInstructionsMap(instructionsMatcher.group(1));
            String paragraph = normalised.substring(lastPrompt, instructionsMatcher.start());
            if (!paragraph.isBlank()) {
                System.out.println(lastPrompt + ": " + paragraph);
                lastPrompt = instructionsMatcher.end();
            }

        }
        return out;
    }

    private Map<String, String[]> createInstructionsMap(String match) {
        Map<String, String[]> out = new HashMap<>();
        String[] instructionList = match.split(",");
        for (String instruction : instructionList) {
            String[] commandArgs = instruction.split(":");
            String command = commandArgs[0];
            String[] args = new String[commandArgs.length - 1];
            if (args.length > 0) {
                System.arraycopy(commandArgs, 1, args, 0, args.length);
            }
            out.put(command, args);
        }
        return out;
    }


    public Map<String, String> getVariables() {
        return variables;
    }
}


//            if (instructionsMap.containsKey("prompt")) {
//                    out.add(currentPrompt);
//                    options = new ArrayList<>();
//        currentPrompt = new Prompt(normalised.substring(lastPrompt, instructionsMatcher.start()), options);
//        lastPrompt = instructionsMatcher.end();
//        } else if (instructionsMap.containsKey("choice")) {
//        String[] args = instructionsMap.get("choice");
//        if (args.length>0){
//        currentChoice = new ChoiceOption(args[0],);
//
//        }
//        options.add(currentChoice);
//        context.setCurrentPrompt(currentPrompt);
//        }