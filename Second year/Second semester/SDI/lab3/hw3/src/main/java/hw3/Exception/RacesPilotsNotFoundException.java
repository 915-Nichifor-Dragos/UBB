package hw3.Exception;

public class RacesPilotsNotFoundException extends RuntimeException {

    public RacesPilotsNotFoundException(Long id) {
        super("Could not find races pilots " + id);
    }
}