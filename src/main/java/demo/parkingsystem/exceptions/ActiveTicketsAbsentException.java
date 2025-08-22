package demo.parkingsystem.exceptions;

public class ActiveTicketsAbsentException extends RuntimeException {
    public ActiveTicketsAbsentException(String message) {
        super(message);
    }
}
