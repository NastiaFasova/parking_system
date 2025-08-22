package demo.parkingsystem.service.impl;

import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.repository.VehicleRepository;
import demo.parkingsystem.service.VehicleFactoryService;
import demo.parkingsystem.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleFactoryService vehicleFactoryService;

    @Override
    public Vehicle getVehicleForCheckIn(String licensePlate, VehicleType vehicleType) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(licensePlate);
        return vehicle.orElseGet(()
                -> vehicleRepository.save(vehicleFactoryService.createVehicle(vehicleType, licensePlate)));
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
