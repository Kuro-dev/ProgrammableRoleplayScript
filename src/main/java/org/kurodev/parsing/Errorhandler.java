package org.kurodev.parsing;

import java.nio.file.Path;

/**
 * @see ParserMode
 */
public interface Errorhandler {
    void onError(String message, Path file);
}
