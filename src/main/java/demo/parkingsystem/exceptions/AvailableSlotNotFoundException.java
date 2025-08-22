package demo.parkingsystem.exceptions;

public class AvailableSlotNotFoundException extends RuntimeException {
    public AvailableSlotNotFoundException(String message) {
        super(message);
    }
}
