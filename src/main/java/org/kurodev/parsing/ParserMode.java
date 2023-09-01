package org.kurodev.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public enum ParserMode implements Errorhandler {
    STRICT {
        /**
         * Will throw a ParseException if any variable, prompt or instruction cannot be resolved.
         */
        @Override
        public void onError(String message, Path file) {
            logger.error("{}: {}", file.getFileName(), message);
            throw new RuntimeException(message);
        }
    },
    /**
     * Will Log errors, but doesn't throw exceptions
     */
    LENIENT {
        @Override
        public void onError(String message, Path file) {
            logger.warn("{}: {}", file.getFileName(), message);
        }
    },
    /**
     * Will neither throw nor report any errors.
     * Not recommended
     */
    SILENT {
        @Override
        public void onError(String message, Path file) {
            //do nothing
        }
    };
    private static final Logger logger = LoggerFactory.getLogger(ParserMode.class);
}

