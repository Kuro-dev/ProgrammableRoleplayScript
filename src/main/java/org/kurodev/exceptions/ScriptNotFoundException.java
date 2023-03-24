package org.kurodev.exceptions;

import java.nio.file.Path;

public class ScriptNotFoundException extends RuntimeException {
    public ScriptNotFoundException(Path s) {
        super(s.toString() + " not found");
    }
}
