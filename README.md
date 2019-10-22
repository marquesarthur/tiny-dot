# Yet another tiny-dot parser


This is a modified version of the [CPSC410 tiny-dot](https://drive.google.com/file/d/1Wfo7MH5IN9i_F7D4A66YSnnks7Rx8GqJ/view) parser, build by Elisa Baniassad.

It's major differences are:

1. It **doesn't** have a main class and instead, it relies on test cases
1. It has **lots** of test cases for the size of the project. 
    * As a software developer it is not enough to stress out how much unit testing is important to software development    
    * I leave this [meme](https://miro.medium.com/max/642/0*2x368zcCx_aSL57K.) as a last resort convincing you about unit testing
1. It's built as a [Maven](https://maven.apache.org/) project
1. It uses some of the Java 9 syntax with [streams](https://www.baeldung.com/java-9-stream-api) (though I'm not that confident about this one)
1. Its tokenizer is **less** powerful than the one that Elisa has
