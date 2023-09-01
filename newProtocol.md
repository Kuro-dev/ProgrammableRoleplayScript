# 1. Requirements

The initial version of the application shall define a simple yet extensible format for storing dynamic stories. A parser
for said format should also be implemented. Finally, a persistent state system should be implemented.

## 1.1 Format
The storing format shall be:
1. human legible (editable / writeable without dedicated tools),
2. uniform format of injectables (options, variables),
3. extensible - ensure new injectables and parameters can be added easily (easily implemented if uniformity is achieved),
4. allow for basic Markdown inside story text (outside injectables)

## 1.2 Parser
The parser shall be:
1. modular - ideally main parser that extracts the injectables from the text and dedicated parsers for each specific injectable,
2. robust - well-defined legal and illegal cases that the parser will either handle or simply return an error on (escaping and stuff),

## 1.3 Persistent state
The state shall be:
1. as straightforward as possible - names and types might need to be indexed for eventual UI needs,
2. persistent? (allows for resuming your reading session from where you left of)


# 2. Samples

## 2.1 Planned Markdown support

### 2.1.1 Headings
```
# Heading 1
## Heading 2
### Heading 3
```

### 2.1.2 Text formatting
```
**This text is bold**
*This text is italic*
```

## 2.2 Miscellaneous

### 2.2.1 Comments
```
/*/ This is a one line comment
```

## 2.3 Injectables

Presently the idea is to use curly braces (`{injectable}`) to denote injectables. This means curly braces would be disallowed in
the story text itself, unless escaping is implemented. The format could also be changed to use additional symbols on top
or curly braces, e.g. a dollar sign (`${injectable}` ) which should be much more rare in human text.

### 2.3.1 Predefined injectables
```
1. Prompt definition:               {prompt:name}
1. Load a prompt:                   {goto:nameOfPrompt}
2. Load a file:                     {load:nameOfFileWithoutExtension.nameOfPrompt}
3. Denote a choice                  {choice:name}
4. Hide a choice on condition x     {choice:name:hideCondition}
5. Allow variables in text          {var:name}
```

TBD:
```
1. Display image:                   {img:referenceFromResource}
```

## 2.4 Sample document

TODO: A longer example should be written / found that also demonstrates separation into several files and such.

```
{prompt:main}
You are sitting at the beach, resting, after a long journey from {var:startingLocation}. Suddenly you spot a bottle floating in the sea. You think to 
yourself that it might have a message inside. Maybe a treasure map. You let out a laugh and mutter to yourself "As if...".

What do you do?
{choice:mcPickedBottle} Check out the bottle, maybe there is something inside after all... {goto:checkOutBottle}
{choice:mcIgnoredBottle} Ignore the bottle
```
