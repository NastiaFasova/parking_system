package demo.parkingsystem.factory.slot;

import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleSlotFactory implements ParkingSlotFactory {

    @Override
    public ParkingSlot createParkingSlot(Level level, Long slotNumber) {
        validateSlotCreation(level, slotNumber);

        ParkingSlotId slotId = new ParkingSlotId(
                level.getId().getParkingLotId(),
                level.getId().getLevelNumber(),
                slotNumber
        );
        return new ParkingSlot(slotId, ParkingSlotType.MOTORCYCLE, level, false);
    }

    @Override
    public boolean supports(ParkingSlotType type) {
        return ParkingSlotType.MOTORCYCLE.equals(type);
    }

    @Override
    public ParkingSlotType getSlotType() {
        return ParkingSlotType.MOTORCYCLE;
    }
}
