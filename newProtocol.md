
# H1
## H2
### H3

**bold**
*italics*

/*/ comment

load a prompt:  {goto:nameOfPrompt}
load a file:    {load:nameOfFileWithoutExtension.nameOfPrompt}
denote a choice {choice:name}
hide a choice on condition x
allow variables in text

[title](https://www.example.com)
![alt text](image.jpg)


{img:

```
{prompt:id}
Lorem ipsum dolor sit amet,
consectetur adipiscing elit, 
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco
laboris nisi ut aliquip ex ea commodo consequat.
Duis aute irure dolor in reprehenderit in
**{var:myVariable1}**
voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident,
sunt in culpa qui officia deserunt mollit anim id est laborum.
{choice:myChoice1} Please choose me
this is another line of the choice
rasdtsdt
sdt
{choice:myChoice2} Pleae *don't* choose me,
I'm shy.

...

No, really...
{choice:myChoice3} no, choose me!

{prompt:id2}
{
{
{
```
