package demo.parkingsystem.factory.slot;

import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.enums.ParkingSlotType;

public interface ParkingSlotFactory {
    ParkingSlot createParkingSlot(Level level, Long slotNumber);
    boolean supports(ParkingSlotType type);
    ParkingSlotType getSlotType();

    default void validateSlotCreation(Level level, Long slotNumber) {
        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }
        if (slotNumber <= 0) {
            throw new IllegalArgumentException("Slot number must be positive");
        }
    }
}
