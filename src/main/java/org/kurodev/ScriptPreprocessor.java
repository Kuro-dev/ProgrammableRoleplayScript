package org.kurodev;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptPreprocessor {
    private static final Pattern COMMENT = Pattern.compile("(\\/\\*\\/.*$)");
    private static final Pattern VARIABLE = Pattern.compile("\\{var:([\\w_]+)}");
    private static final String NEW_LINE = "\n";

    /**
     * Strips comments and trims lines
     *
     * @param script    The script to normalize
     * @param variables The variables to replace {var:id} entries with
     * @return the normalised script
     */
    public static String run(String script, Map<String, String> variables) {
        return processVariables(normaliseScript(script), variables);
    }

    protected static String normaliseScript(String script) {
        var lines = script.split(NEW_LINE);
        var normalisedLines = new ArrayList<String>();

        for (var line : lines) {
            var normalisedLine = line.replaceAll(COMMENT.pattern(), "").trim();

            // line.isBlank() ensures original empty lines are respected
            if (!normalisedLine.isBlank() || line.isBlank()) {
                normalisedLines.add(normalisedLine);
            }
        }

        return String.join(NEW_LINE, normalisedLines);
    }

    private static String processVariables(String script, Map<String, String> variables) {
        final int varIndex = 1;
        var matcher = VARIABLE.matcher(script);
        StringBuilder result = new StringBuilder();
        int lastMatch = 0;
        while (matcher.find()) {
            String varName = matcher.group(varIndex);
            String value = variables.get(varName);
            if (Objects.nonNull(value)) {
                result.append(script, lastMatch, matcher.start());
                result.append(value);
                lastMatch = matcher.end();
            }
        }
        result.append(script, lastMatch, script.length());
        return result.toString();
    }
}
