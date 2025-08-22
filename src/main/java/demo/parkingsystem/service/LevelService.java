package demo.parkingsystem.service;

import demo.parkingsystem.dto.level.LevelWithSlotsDto;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.enums.ParkingSlotType;

public interface LevelService {
    LevelWithSlotsDto addParkingSlot(Long parkingLotId, Long levelId, ParkingSlotType parkingSlotType);
    LevelWithSlotsDto removeParkingSlot(Long parkingLotId, Long levelId, Long parkingSlotId);
    Level findById(LevelId levelId);
}
