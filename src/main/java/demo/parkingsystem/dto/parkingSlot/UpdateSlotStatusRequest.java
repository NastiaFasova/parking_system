package demo.parkingsystem.dto.parkingSlot;

import lombok.Data;

@Data
public class UpdateSlotStatusRequest {
    private boolean isOccupied;
}
