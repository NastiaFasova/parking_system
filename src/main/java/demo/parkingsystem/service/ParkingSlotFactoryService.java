package demo.parkingsystem.service;

import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.enums.ParkingSlotType;

public interface ParkingSlotFactoryService {
    ParkingSlot createParkingSlot(ParkingSlotType type, Level level);
}
