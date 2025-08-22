package demo.parkingsystem.service.impl;

import demo.parkingsystem.factory.slot.ParkingSlotFactory;
import demo.parkingsystem.factory.vehicle.VehicleFactory;
import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.service.VehicleFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehicleFactoryServiceImpl implements VehicleFactoryService {
    private final Map<VehicleType, VehicleFactory> factoryMap;

    @Autowired
    public VehicleFactoryServiceImpl(List<VehicleFactory> factories) {
        this.factoryMap = factories.stream()
                .collect(Collectors.toMap(
                        VehicleFactory::getVehicleType,
                        factory -> factory
                ));
    }

    @Override
    public Vehicle createVehicle(VehicleType type, String licensePlate) {
        VehicleFactory factory = factoryMap.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("No factory found for vehicle type: " + type);
        }

        return factory.createVehicle(licensePlate);
    }
}
