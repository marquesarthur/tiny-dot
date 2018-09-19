package cs.ubc.ca.analysis;

import cs.ubc.ca.parser.EdgeNode;
import cs.ubc.ca.parser.ShapeNode;

import java.util.Observable;
import java.util.Observer;

public class MissingDeclarationListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ShapeNode) {
            ShapeNode shapeNode = (ShapeNode) arg;
        } else if (arg instanceof EdgeNode) {
            EdgeNode edgeNode = (EdgeNode) arg;
        }
    }
}
