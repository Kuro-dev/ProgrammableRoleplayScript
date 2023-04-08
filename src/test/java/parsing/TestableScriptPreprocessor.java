package parsing;

import org.kurodev.ScriptPreprocessor;

/**
Exposes the ScriptPreprocessor internal methods for unit testing.
 */
public class TestableScriptPreprocessor extends ScriptPreprocessor {
    public static String normaliseScriptPublic(String script) {
        return normaliseScript(script);
    }
}