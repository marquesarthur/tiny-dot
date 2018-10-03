package cs.ubc.ca.dsl;

public enum ProgramOutputStatus {
    SUCCESS(0),
    ERROR(1);

    private final int statusCode;

    ProgramOutputStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
