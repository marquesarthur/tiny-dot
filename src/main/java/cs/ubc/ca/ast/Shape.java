package cs.ubc.ca.ast;

public class Shape {

    private String geoShape;

    private String name;

    private static final String TEMPLATE = "%s[shape=%s]" + System.lineSeparator();

    public String getGeoShape() {
        return geoShape;
    }

    public void setGeoShape(String geoShape) {
        this.geoShape = geoShape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toDigraph() {
        return String.format(TEMPLATE, name, geoShape);
    }
}
