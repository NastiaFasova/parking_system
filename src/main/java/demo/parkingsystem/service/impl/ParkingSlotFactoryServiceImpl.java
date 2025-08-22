package demo.parkingsystem.service.impl;

import demo.parkingsystem.factory.slot.ParkingSlotFactory;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.service.ParkingSlotFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParkingSlotFactoryServiceImpl implements ParkingSlotFactoryService {
    private final Map<ParkingSlotType, ParkingSlotFactory> factoryMap;

    @Autowired
    public ParkingSlotFactoryServiceImpl(List<ParkingSlotFactory> factories) {
        this.factoryMap = factories.stream()
                .collect(Collectors.toMap(
                        ParkingSlotFactory::getSlotType,
                        factory -> factory
                ));
    }

    @Override
    public ParkingSlot createParkingSlot(ParkingSlotType type, Level level) {
        ParkingSlotFactory factory = factoryMap.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("No factory found for slot type: " + type);
        }

        return factory.createParkingSlot(level, calculateNextSlotNumber(level));
    }

    private Long calculateNextSlotNumber(Level level) {
        if (level.getParkingSlots().isEmpty()) {
            return 1L;
        }

        long maxSlot = level.getParkingSlots().stream()
                .mapToLong(slot -> slot.getId().getSlotNumber())
                .max()
                .orElse(0L);

        return maxSlot + 1;
    }
}
