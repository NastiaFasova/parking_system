package demo.parkingsystem.factory.vehicle;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class CarFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(String licensePlate) {
        validateCarLicensePlate(licensePlate);
        return new Vehicle(VehicleType.CAR, licensePlate.toUpperCase());
    }

    @Override
    public boolean supports(VehicleType type) {
        return VehicleType.CAR.equals(type);
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.CAR;
    }

    private void validateCarLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("Car license plate cannot be empty");
        }
        if (!licensePlate.matches("^[A-Z0-9]{6,8}$")) {
            throw new IllegalArgumentException("Invalid car license plate format");
        }
    }
}
