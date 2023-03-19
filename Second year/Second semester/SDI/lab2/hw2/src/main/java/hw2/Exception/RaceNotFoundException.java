package hw2.Exception;

public class RaceNotFoundException extends RuntimeException {

    public RaceNotFoundException(Long id) {
        super("Could not find race " + id);
    }
}

