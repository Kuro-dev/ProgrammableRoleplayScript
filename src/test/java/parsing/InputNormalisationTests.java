package parsing;

import org.junit.Assert;
import org.junit.Test;

public class InputNormalisationTests {

    @Test
    public void emptyScript_doNothing() {
        var rawScript = "";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(normalisedScript);
        Assert.assertEquals(rawScript, normalisedScript);
    }

    @Test
    public void whitespaceScript_trim() {
        var rawScript = "     ";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(normalisedScript);
        Assert.assertEquals("", normalisedScript);
    }

    @Test
    public void whitespaceAtStartOfScript_trimStart() {
        var rawScript = " This line is prefixed with whitespace and should be trimmed.";

        var expectedScript = "This line is prefixed with whitespace and should be trimmed.";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(normalisedScript);
        Assert.assertEquals(expectedScript, normalisedScript);
    }

    @Test
    public void singleLineScriptWithNoComments_doNothing() {
        var rawScript = "Hello, this is a single line text.";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(rawScript);
        Assert.assertEquals(rawScript, normalisedScript);
    }

    @Test
    public void singleLineCommentScript_removeComment() {
        var rawScript = "/*/ Hello, this is a single line comment.";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(rawScript);
        Assert.assertEquals("", normalisedScript);
    }

    @Test
    public void singleLineScriptWithInlineComment_RemoveCommentAndTrimEnd() {
        var rawScript = "Hello, this is a single line. /*/ Hello, this is a single line comment.";

        var expectedScript = "Hello, this is a single line.";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(rawScript);
        Assert.assertEquals(expectedScript, normalisedScript);
    }

    @Test
    public void multilineScriptWithNoComments_doNothing() {
        var rawScript = """
                {prompt:main}
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Eget est lorem ipsum dolor sit amet consectetur adipiscing. Duis ultricies lacus sed turpis tincidunt id aliquet. Donec ultrices tincidunt arcu non sodales neque. Turpis massa sed elementum tempus egestas. Vulputate eu scelerisque felis imperdiet proin fermentum leo. Aliquet eget sit amet tellus cras adipiscing enim eu. Amet est placerat in egestas erat imperdiet sed euismod. Adipiscing diam donec adipiscing tristique risus. Aliquam purus sit amet luctus venenatis lectus magna fringilla urna. Scelerisque purus semper eget duis at tellus at. Morbi tristique senectus et netus et malesuada. Et tortor at risus viverra adipiscing at in tellus. Consectetur adipiscing elit duis tristique sollicitudin nibh sit.

                Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere. Vestibulum lorem sed risus ultricies. Sem fringilla ut morbi tincidunt augue interdum velit euismod in. Et ultrices neque ornare aenean euismod elementum nisi quis. Lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi. Sapien nec sagittis aliquam malesuada bibendum arcu. Facilisi etiam dignissim diam quis enim. Amet justo donec enim diam vulputate ut pharetra sit amet. Nisi scelerisque eu ultrices vitae. Urna condimentum mattis pellentesque id nibh tortor. Tristique senectus et netus et malesuada fames ac turpis egestas.

                Morbi leo urna molestie at. Pharetra magna ac placerat vestibulum lectus mauris ultrices eros in. Dolor magna eget est lorem ipsum dolor sit amet consectetur. Morbi tempus iaculis urna id. Sit amet luctus venenatis lectus magna fringilla urna. Sed lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt. Libero justo laoreet sit amet cursus sit amet dictum. Metus dictum at tempor commodo ullamcorper a lacus vestibulum sed. Enim nec dui nunc mattis enim ut tellus elementum. Ipsum faucibus vitae aliquet nec ullamcorper sit amet risus. Pharetra et ultrices neque ornare aenean.

                Malesuada bibendum arcu vitae elementum curabitur vitae nunc sed velit {var:currentWeather}. Nibh ipsum consequat nisl vel pretium lectus quam id leo. Odio morbi quis commodo odio aenean. Mauris augue neque gravida in fermentum et sollicitudin ac orci. Sed risus pretium quam vulputate dignissim suspendisse. Tempor nec feugiat nisl pretium. Facilisis sed odio morbi quis commodo odio aenean sed. Vulputate sapien nec sagittis aliquam malesuada bibendum arcu. Sed lectus vestibulum mattis ullamcorper velit sed. A cras semper auctor neque vitae. Viverra aliquet eget sit amet tellus cras adipiscing enim. Curabitur gravida arcu ac tortor dignissim convallis aenean et tortor. Vel facilisis volutpat est velit. Senectus et netus et malesuada fames ac turpis egestas maecenas.

                Viverra nam libero justo laoreet sit amet cursus. Commodo viverra maecenas accumsan lacus vel facilisis volutpat est. Pharetra sit amet aliquam id. Volutpat maecenas volutpat blandit aliquam etiam erat velit. Purus sit amet luctus venenatis lectus magna fringilla. Auctor urna nunc id cursus metus aliquam eleifend mi. Enim praesent elementum facilisis leo vel fringilla. Non curabitur gravida arcu ac tortor dignissim convallis aenean. Turpis cursus in hac habitasse platea dictumst quisque sagittis. Duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam. Et netus et malesuada fames ac turpis egestas integer. Erat imperdiet sed euismod nisi.

                {choice:myFirstChoiceEver} Adipiscing diam donec adipiscing tristique risus.
                {choice:mySecondChoice} Pharetra sit amet aliquam id. Volutpat maecenas volutpat blandit aliquam etiam erat velit.""";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(rawScript);
        Assert.assertEquals(rawScript, normalisedScript);
    }

    @Test
    public void multilineScriptWithComments_removeCommentsAndTrimEnd() {
        var rawScript = """
                {prompt:main} First text line!
                Second text line.
                /*/ This is a comment, please ignore me
                Third text line?
                /*/ Another comment /*/ - useless comment tag
                Fourth text line with variable {var:myVariable}.
                Fifth line with comment at the end /*/ comment here, should be removed
                /**/ Invalid comment line""";

        var expectedScript = """
                {prompt:main} First text line!
                Second text line.
                Third text line?
                Fourth text line with variable {var:myVariable}.
                Fifth line with comment at the end
                /**/ Invalid comment line""";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(normalisedScript);
        Assert.assertEquals(expectedScript, normalisedScript);
    }

    @Test
    public void scriptWithRandomSymbols_doNothing() {
        var rawScript = "~!@#$%^&*()_+/\\`€/**/////[]{}\"\"''<>";

        var normalisedScript = TestableScriptPreprocessor.normaliseScriptPublic(rawScript);

        Assert.assertNotNull(normalisedScript);
        Assert.assertEquals(rawScript, normalisedScript);
    }
}
