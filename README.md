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
1. It has semantic checking for some simple scenarios, like redeclaring variables or missing identifiers

I did not dedicate many hours to this project. I do apologize if something is not clear enough. Please, fill a GitHub issue if you see a bug or if something is blurry and could be refined.

The next sections detail some of the core classes and methods about the project.

## DotProgram

It's the core class of our DSL. It contains methods for parsing and compile a source file into a target `dot` program. 

* All input files should live under `src/main/resources`
* All output files are generated under `src/main/resources/build`

# Parsing

## Tokenizer

It's mostly a [stack](https://www.geeksforgeeks.org/stack-data-structure/). Nothing special here other than the `preProcessInput` input method. It:

1. Replace line separators with line separators enclosed by empty spaces
1. Split the tokens by spaces
1. Filter empty tokens. e.g. `two  spaces  here`
    * generates  `["two", "spaces", "here"]` 
    * instead of `["two", "", "spaces", "", "here"]`


## Node

Implements the [interpreter pattern](https://www.geeksforgeeks.org/interpreter-design-pattern/). The original tiny-dot parser uses a Singleton as the context, but I decided to stick with the context as a parameter. There are trade-offs with each decision, pick whatever you prefer.

* `DigraphNode` is the root of the AST and it contains `ShapeNode`s or `EdgeNode`s as children

### ShapeNode & EdgeNode

Both have an expression array that takes Strings and regex as an input. In the parse method, the tokenizer walks through the tokens evaluating whether they match the expected expressions. in case they don't, a `ParseException` is thrown.


## Symbol Table

Once the AST is built and we have a `root`, we use the `AstVisitor` class to traverse the tree. While visiting each node, the AstVisitor somewhat implements the observer pattern, i.e. everytime we leap to a new node we signal that to someone that is observing the tree traversal. In this case, the someone is our `SymbolTable` and it adds variables to itself when needed be.

Why should I use the Observer pattern? 

1. It makes the `AstVisitor` class a straightforward tree traversal class
1. It decouples the logic of buidling the symbol table and traversing the tree
1. It favors reusability. If I need several observers, I can implement them as separated classes and add each one as an observer of the tree traversal
1. It favors performance. Rather than traversing the tree several times. All the observers watch for events of their interest while the tree is traversed only once. More on that later.


**IMPORTANT:** JDK 9 [deprecated the Observer pattern](https://bugs.openjdk.java.net/browse/JDK-8154801) due to a serie of issues. It's not a problem with the pattern itself but rather a problem on how Java implemented it. **IIRC**


# Compiling

## Semantic Analysis

You can build a very simple and parsable input which has no parse errors but does not produce anything and your DSL should warn you about this compile errors. For instance:

```
make me a circle called Fido please
make me a square called Biff please
connect Foo to Bar
```

Is a syntatically correct output. However, neither `Foo` nor `Bar` are declared variables and if you want to help people using your language, your parser should warn them about that. That is the [**semantic analysis**](https://en.wikipedia.org/wiki/Semantic_analysis_(compilers)) part of a compiler. 

### MissingDeclarationListener & RedeclarationListener

As there are several analysis that you can make, such as type checking, variables redeclaration, or missing identifiers, we can use the Observer pattern once again. We can register several observers to watch for events of interest and traverse the tree only once.

This is achieved using the `MissingDeclarationListener` classes `RedeclarationListener` which evaluate missing identifiers, and redeclaration errors. They keep track of all the errors as a list. 

**IMPORTANT 1:** In an earlier version of the DSL, I was throwing exceptions when I found a missing declaration or redeclaration error. However, this is not what happens normally in IDEs. You don't see only the first compile error but rather all of them. Therefore, I decided to change it to a list.

**IMPORTANT 2:** As I am a **lazy person**, `CompileError` still extends from `RuntimeException`. PLEASE, don't do that.

## Transformation

each one of the nodes is responsible for generating the code related to that node.

### DigraphNode

Only generates the digraph declaration block:

```
digraph G {

}
```

All the responsibility of generating code related to its children should be delegated to them. This is done using JDK 9 for each syntax:

```
children.forEach(Node::compile);
```

This is super awesome! As all children implement the interpreter pattern, code transformation is achieved in a simple yet elegant way.


### ShapeNode

Only generates the shape declaration blocks:

```
Fido[shape=circle]
```


### EdgeNode

Only generates the edge connection blocks:

```
Fido->Biff
```