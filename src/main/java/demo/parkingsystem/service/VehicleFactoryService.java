package demo.parkingsystem.service;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;

public interface VehicleFactoryService {
    Vehicle createVehicle(VehicleType type, String licensePlate);
}
