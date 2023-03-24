package org.kurodev;

import org.kurodev.choice.ChoiceOption;
import org.kurodev.choice.ChoiceParser;
import org.kurodev.choice.ChoicePickedCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KChoices {
    private static final Pattern COMMENT = Pattern.compile("(?<!\\\\)(#.*$)");
    private static final Logger logger = LoggerFactory.getLogger(KChoices.class);
    private final Map<String, String> args;
    private final List<String> previousChoices = new ArrayList<>();
    PromptLoader page = null;
    private Function<String, Path> pathProvider = s -> Path.of("./scripts/" + s + ".krp");
    private Prompt prompt = null;

    public KChoices() {
        this(new HashMap<>());
    }

    public KChoices(Map<String, String> args) {
        this.args = args;
    }

    /**
     * comments have been removed leading and trailing whitespaces too.
     * (comments are denoted by #)
     *
     * @return a list with only the relevant text, no comments.
     * @apiNote if a # symbol is needed in the text, it can be escaped by using \#
     */
    private static List<String> normalizeLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var matcher = COMMENT.matcher(line);
            while (matcher.find()) {
                var grp = matcher.group();
                line = line.replace(grp, "");
            }
            lines.set(i, line.replace("\\#", "#").trim());
        }
        return lines.stream().filter(s -> !s.isBlank()).collect(Collectors.toList());

    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
        handleInstructions();
    }

    public void start(InputStream input) {
        var reader = new BufferedReader(new InputStreamReader(input));
        List<String> lines = reader.lines().collect(Collectors.toList());
        start(lines);
    }

    private void start(List<String> lines) {
        logger.trace("Starting the game");
        page = new PromptLoader(String.join("\n", normalizeLines(lines)));
        prompt = page.loadPrompt("main");
        if (prompt == null) {
            throw new RuntimeException("The script must contain a '[main]' prompt");
        }
        handleInstructions();
    }

    private void handleInstructions() {
        logger.trace("Loading instructions");
        for (ChoiceOption option : prompt.getOptions()) {
            var parser = new ChoiceParser(option);
            parser.parse(this, page);
        }
    }

    public void pickChoice(String choiceID) {
        logger.trace("User picked: {}", choiceID);
        var option = prompt.getOptions().stream()
                .filter(choiceOption -> choiceOption.getId().equals(choiceID))
                .findFirst().orElse(null);
        if (option == null)
            throw new IllegalArgumentException("Must be a choice that is offered by the prompt");
        previousChoices.add(prompt.getId() + "." + choiceID);
        for (ChoicePickedCallback callback : option.getCallbacks()) {
            callback.onSelected(this, page);
        }
        handleInstructions();
    }

    public void pickChoice(ChoiceOption option) {
        pickChoice(option.getId());
    }

    public List<String> getPreviousChoices() {
        return Collections.unmodifiableList(previousChoices);
    }

    public void start(String script) {
        start(Arrays.asList(script.split("[\n\r]")));
    }

    public Function<String, Path> getPathProvider() {
        return pathProvider;
    }

    /**
     * @param pathProvider A function to guide the software from where to load the next script.
     *                     The string given to the function is the name of the file as provided by the
     *                     $(load:filenameWithoutExtention) instruction
     *                     <p>Must not be {@code null}</p>
     */
    public void setPathProvider(Function<String, Path> pathProvider) {
        this.pathProvider = Objects.requireNonNull(pathProvider);
    }

    public void setPage(PromptLoader promptLoader) {
        this.page = promptLoader;
    }
}
