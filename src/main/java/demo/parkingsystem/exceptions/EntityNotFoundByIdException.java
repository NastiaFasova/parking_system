package demo.parkingsystem.exceptions;

public class EntityNotFoundByIdException extends RuntimeException {
    public EntityNotFoundByIdException(String message) {
        super(message);
    }
}
