package demo.parkingsystem.factory.slot;

import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import org.springframework.stereotype.Component;

@Component
public class HandicappedSlotFactory implements ParkingSlotFactory {

    @Override
    public ParkingSlot createParkingSlot(Level level, Long slotNumber) {
        validateSlotCreation(level, slotNumber);

        ParkingSlotId slotId = new ParkingSlotId(
                level.getId().getParkingLotId(),
                level.getId().getLevelNumber(),
                slotNumber
        );

        ParkingSlot slot = new ParkingSlot(slotId, ParkingSlotType.HANDICAPPED, level, false);

        return slot;
    }

    @Override
    public boolean supports(ParkingSlotType type) {
        return ParkingSlotType.HANDICAPPED.equals(type);
    }

    @Override
    public ParkingSlotType getSlotType() {
        return ParkingSlotType.HANDICAPPED;
    }
}
