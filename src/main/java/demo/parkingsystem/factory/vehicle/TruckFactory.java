package demo.parkingsystem.factory.vehicle;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class TruckFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(String licensePlate) {
        validateTruckLicensePlate(licensePlate);
        return new Vehicle(VehicleType.TRUCK, licensePlate.toUpperCase());
    }

    @Override
    public boolean supports(VehicleType type) {
        return VehicleType.TRUCK.equals(type);
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TRUCK;
    }

    private void validateTruckLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("Truck license plate cannot be empty");
        }
        if (!licensePlate.matches("^[A-Z0-9]{7,10}$")) {
            throw new IllegalArgumentException("Invalid truck license plate format");
        }
    }
}
