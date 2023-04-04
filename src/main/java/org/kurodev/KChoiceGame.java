package org.kurodev;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KChoiceGame {
    /**
     * mark / * / and everything after as comment
     */

    private final Map<String, String> variables = new HashMap<>();

    public Map<String, String> getVariables() {
        return variables;
    }

    public void start(String script) {
        String replaced = ScriptPreprocessor.run(script, variables);
        //parsing should follow this, probably in a separate java class dedicated to parsing
    }


    public void start(InputStream script) {
        start(new BufferedReader(new InputStreamReader(script)).lines().collect(Collectors.joining("\n")));
    }
}
