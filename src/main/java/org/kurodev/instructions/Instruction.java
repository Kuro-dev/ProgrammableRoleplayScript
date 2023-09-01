package org.kurodev.instructions;

import org.kurodev.parsing.ParserContext;

/**
 * Primary interface to denote any Instruction or Injectable.
 */
public interface Instruction {
    void apply(ParserContext context, String[] args, String raw);

    String getName();
}
