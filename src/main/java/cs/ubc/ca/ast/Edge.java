package cs.ubc.ca.ast;

public class Edge {

    private String a;

    private String b;

    private static final String TEMPLATE = "%s->%s" + System.lineSeparator();

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public void connect(String token) {
        if (this.a == null) {
            this.setA(token);
        } else {
            this.setB(token);
        }
    }

    public String toDigraph(){
        return String.format(TEMPLATE, a, b);
    }
}
