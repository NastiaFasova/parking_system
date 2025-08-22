package demo.parkingsystem.service;

import demo.parkingsystem.dto.parkingSlot.ParkingSlotDto;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.ParkingSlotId;

public interface ParkingSlotService {
    ParkingSlot findById(ParkingSlotId parkingSlotId);
    ParkingSlot save(ParkingSlot parkingSlot);
    ParkingSlotDto isOccupied(Long parkingLotId, Long levelId, Long parkingSlotId, boolean isOccupied);
}
