package cybersoft.javabackend.java18.crm.exception;

public class DatabaseNotFoundException extends RuntimeException {

    public DatabaseNotFoundException() {
        super();
    }

    public DatabaseNotFoundException(String message) {
        super(message);
    }
}
