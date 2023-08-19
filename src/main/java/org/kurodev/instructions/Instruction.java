package org.kurodev.instructions;

import java.util.List;

public interface Instruction {
    String getText();

    List<ScriptCallbackFunction> onLoad();
    List<ScriptCallbackFunction> onUnload();
}
