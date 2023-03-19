package hw2.Exception;

public class PilotNotFoundException extends RuntimeException {

    public PilotNotFoundException(Long id) {
        super("Could not find pilot " + id);
    }
}
