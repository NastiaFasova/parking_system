package demo.parkingsystem.factory.vehicle;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;

public interface VehicleFactory {
    Vehicle createVehicle(String licensePlate);
    boolean supports(VehicleType type);
    VehicleType getVehicleType();
}
