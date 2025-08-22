package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.level.LevelWithSlotsDto;
import demo.parkingsystem.mapper.LevelMapper;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.repository.LevelRepository;
import demo.parkingsystem.service.LevelService;
import demo.parkingsystem.service.ParkingSlotFactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;
    private final ParkingSlotFactoryService parkingSlotFactoryService;

    @Override
    public LevelWithSlotsDto addParkingSlot(Long parkingLotId, Long levelId, ParkingSlotType parkingSlotType) {
        LevelId levelIdObj = new LevelId(parkingLotId, levelId);
        Level level = findById(levelIdObj);

        ParkingSlot parkingSlot = parkingSlotFactoryService.createParkingSlot(parkingSlotType, level);
        level.getParkingSlots().add(parkingSlot);
        return levelMapper.toDto(levelRepository.save(level));
    }

    @Override
    public LevelWithSlotsDto removeParkingSlot(Long parkingLotId, Long levelId, Long parkingSlotId) {
        LevelId levelIdObj = new LevelId(parkingLotId, levelId);
        Level level = findById(levelIdObj);

        ParkingSlotId slotId = new ParkingSlotId(parkingLotId, levelId, parkingSlotId);

        level.getParkingSlots().removeIf(p -> p.getId().equals(slotId));
        return levelMapper.toDto(levelRepository.save(level));
    }

    @Override
    public Level findById(LevelId levelId) {
        log.info("Searching for Level with ID: {}", levelId);
        log.info("ParkingLotId: {} (type: {})", levelId.getParkingLotId(), levelId.getParkingLotId().getClass().getSimpleName());
        log.info("LevelNumber: {} (type: {})", levelId.getLevelNumber(), levelId.getLevelNumber().getClass().getSimpleName());

        // Debug: Check what's actually in the database
        List<Level> allLevels = levelRepository.findAll();
        log.info("All levels in database:");
        for (Level level : allLevels) {
            log.info("Found Level - ParkingLotId: {} (type: {}), LevelNumber: {} (type: {})",
                    level.getId().getParkingLotId(),
                    level.getId().getParkingLotId().getClass().getSimpleName(),
                    level.getId().getLevelNumber(),
                    level.getId().getLevelNumber().getClass().getSimpleName());
        }
        return levelRepository.findById(levelId).orElseThrow(()
                -> new EntityNotFoundByIdException("Level was not found by id: " + levelId));
    }

}
