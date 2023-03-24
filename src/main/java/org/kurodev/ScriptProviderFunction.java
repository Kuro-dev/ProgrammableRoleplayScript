package org.kurodev;

import org.kurodev.exceptions.ScriptNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;

public interface ScriptProviderFunction {
    static ScriptProviderFunction defaultScriptProvider() {
        return filename -> {
            var scriptPath = Path.of("./scripts/" + filename + ".krp");
            if (Files.exists(scriptPath)) {
                return Files.readString(scriptPath);
            } else {
                throw new ScriptNotFoundException(scriptPath);
            }
        };
    }

    /**
     * @param fileName The name of the file that was given in the $(load:filename) instruction
     * @return the content of the new script file.
     * @throws Exception if anything goes wrong.
     */
    String getScript(String fileName) throws Exception;
}
