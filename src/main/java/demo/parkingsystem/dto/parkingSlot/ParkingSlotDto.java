package demo.parkingsystem.dto.parkingSlot;

import demo.parkingsystem.model.enums.ParkingSlotType;
import lombok.Data;

@Data
public class ParkingSlotDto {
//    private Long parkingLotId;
//    private Long levelId;
    private Long parkingSlotNumber;
    private ParkingSlotType type;
    private boolean isOccupied;
}
