package demo.parkingsystem.dto.parkingSlot;

import demo.parkingsystem.model.enums.ParkingSlotType;
import lombok.Data;

@Data
public class AvailableParkingSlotDto {
    private Long levelId;
    private Long parkingSlotNumber;
    private ParkingSlotType type;
    private boolean isOccupied;
}
