package demo.parkingsystem.service;

import demo.parkingsystem.dto.parkingLot.ParkingLotDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotResponseDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotWithLevelsDto;
import demo.parkingsystem.dto.parkingSlot.AvailableParkingSlotDto;
import demo.parkingsystem.model.ParkingLot;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.enums.VehicleType;

import java.util.List;

public interface ParkingLotService {
    ParkingLotResponseDto create(ParkingLotDto parkingLotRequestDto);
    void remove(Long parkingLotId);
    ParkingLotWithLevelsDto addLevel(Long parkingLotId);
    ParkingLotWithLevelsDto removeLevel(Long parkingLotId, Long levelId);
    ParkingLot findById(Long parkingLotId);
    ParkingSlot getAvailableSlot(Long parkingLotId, VehicleType vehicleType);
    List<AvailableParkingSlotDto> geAvailableParkingSlots(Long parkingLotId);
}
