package demo.parkingsystem.factory.vehicle;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(String licensePlate) {
        validateMotorcycleLicensePlate(licensePlate);
        return new Vehicle(VehicleType.MOTORCYCLE, licensePlate.toUpperCase());
    }

    @Override
    public boolean supports(VehicleType type) {
        return VehicleType.MOTORCYCLE.equals(type);
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.MOTORCYCLE;
    }

    private void validateMotorcycleLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("Motorcycle license plate cannot be empty");
        }
        if (!licensePlate.matches("^[A-Z0-9]{4,7}$")) {
            throw new IllegalArgumentException("Invalid motorcycle license plate format");
        }
    }
}
