package demo.parkingsystem.service;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;

public interface VehicleService {
    Vehicle getVehicleForCheckIn(String licensePlate, VehicleType vehicleType);
    Vehicle save(Vehicle vehicle);
}
