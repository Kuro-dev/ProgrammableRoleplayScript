Install via maven: 
```
<dependency>
<groupId>org.kurodev</groupId>
<artifactId>kchoices</artifactId>
<version>1.0-SNAPSHOT</version>
</dependency>
```

# basics:

The Entrypoint of your script is the `[main]` prompt.
Comments in a script are supported. Anything in the line following
a `#` symbol is excluded from the parsing. if you do need a # in the text somewhere it can be escaped using `\#`

```
[promptID] some text for the roleplay
can be multi line too!
  $someOption:this is an option the user can choose// 
  $someOtherOption:or they can choose to go this path
  again these can be multi-line and are terminated by ->//
[$promptID]
```

# how to make choices matter

the text of any choice may include any of these instructions:

- `$(to:promptID)` causes the engine to automatically load the next prompt in this script after a choice has been made.
- `$(if:promptID.choiceID)` hides this choice if the given choice has not been made.
- `($load:scriptName.firstPrompt)` loads the given script file (filename without extention) and automatically loads the
  given prompt after the choice was made. (the given prompt can be omitted, which will cause the parser to default to loading the [main] prompt of the given file)

# example script

(it does not matter where in the choice-text you put the instructions)

```
#example.krp

# this line should be ignored
[main]Youre in a forest, the path crosses off to the left and right[$main]
    $goLeft: you go left $(to:prompt)
    $goRight: $(to:prompt) you go right

[prompt]this should be displayed
this is a second line for this text[$prompt]
    $optionA: this is an option for the "prompt" text     
    #this comment will also be ignored
    $optionB: $(if:main.goRight) this is conditional
```

Loading the script:

```java
import org.kurodev.KChoices;
import org.kurodev.choice.ChoiceOption;

import java.nio.file.Files;
import java.nio.file.Path;

public class example {
  public static void main(String[] args) throws Exception {
    Path scriptFile = Path.of("./scripts/example.krp");
    KChoices game = new KChoices();
    game.start(Files.newInputStream(scriptFile));

    //print the text
    System.out.println(game.getPrompt().getText());
    for (ChoiceOption option : game.getPrompt().getOptions()) {
      System.out.println(option.getText());
    }

    //handle the input to make choices

    game.pickChoice("goLeft");

    //display the new prompt and options

    game.pickChoice("optionA");
  }
}
```

# TODOS:

- add a way to inject arguments such as character names into the script using `{varName}`
- clean up the code once everything is working
- add javadoc
- add documentation showing how to add custom instructions to the roleplay
- add the ability to save and load
