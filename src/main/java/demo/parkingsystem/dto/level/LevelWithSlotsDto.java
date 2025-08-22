package demo.parkingsystem.dto.level;

import demo.parkingsystem.dto.parkingSlot.ParkingSlotDto;
import lombok.Data;

import java.util.List;

@Data
public class LevelWithSlotsDto {
    private Long parkingLotId;
    private Long levelNumber;
    private List<ParkingSlotDto> parkingSlots;
}
