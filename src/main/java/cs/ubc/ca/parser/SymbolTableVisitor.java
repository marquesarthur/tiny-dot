package cs.ubc.ca.parser;

public class SymbolTableVisitor {

    private final Node root;

    private SymbolTable symbols;

    public SymbolTableVisitor(Node node) {
        this.symbols = new SymbolTable();
        this.root = node;
    }

    public void visit(Node node) {
        if (node instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) node;
            this.symbols.insert(shapeNode.getShape().getName(), shapeNode);
        }
        this.visitChildren(node);
    }

    private void visitChildren(Node node) {
        for (Node child : node.getChildren()) {
            this.visit(child);
        }
    }

    public SymbolTable generate() {
        this.visit(this.root);
        return this.symbols;
    }
}