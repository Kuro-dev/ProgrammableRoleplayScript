package org.kurodev;

import java.util.*;
import java.util.regex.Pattern;

public class ScriptPreprocessor {
    private static final Pattern COMMENT = Pattern.compile("((\\/\\*\\/).*$)");
    private static final Pattern VARIABLE = Pattern.compile("\\{var:(.+)}");

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

    private static String normaliseScript(String script) {
        var split = script.split("\n");
        List<String> lines = new ArrayList<>(split.length);
        lines.addAll(Arrays.asList(split));
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var matcher = COMMENT.matcher(line);
            while (matcher.find()) {
                var grp = matcher.group();
                line = line.replace(grp, "");
            }
            lines.set(i, line.trim());
        }
        return String.join("\n", lines);
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
