package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.parkingLot.ParkingLotDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotResponseDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotWithLevelsDto;
import demo.parkingsystem.mapper.ParkingLotMapper;
import demo.parkingsystem.exceptions.AvailableSlotNotFoundException;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingLot;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.repository.ParkingLotRepository;
import demo.parkingsystem.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;

    @Override
    public ParkingLotResponseDto create(ParkingLotDto parkingLotRequestDto) {
        ParkingLot parkingLot = parkingLotRepository.save(parkingLotMapper
                .createParkingLotDtoToParkingDto(parkingLotRequestDto));
        return parkingLotMapper.parkingLotToParkingLotResponseDto(parkingLot);
    }

    @Override
    public void remove(Long parkingLotId) {
        parkingLotRepository.deleteById(parkingLotId);
    }

    @Override
    public ParkingLotWithLevelsDto addLevel(Long parkingLotId) {
        ParkingLot parkingLot = findById(parkingLotId);
        Long nextLevelNumber = calculateNextLevelNumber(parkingLot);
        LevelId levelId = new LevelId(parkingLotId, nextLevelNumber);
        Level newLevel = new Level(levelId, parkingLot);
        newLevel.setParkingLot(parkingLot);
        parkingLot.getLevels().add(newLevel);
        return parkingLotMapper.parkingLotToDtoWithLevels(parkingLotRepository.save(parkingLot));
    }

    @Override
    public ParkingLotWithLevelsDto removeLevel(Long parkingLotId, Long levelId) {
        ParkingLot parkingLot = findById(parkingLotId);
        LevelId levelIdObj = new LevelId(parkingLotId, levelId);
        findById(parkingLotId).getLevels().removeIf(l -> l.getId().equals(levelIdObj));
        return parkingLotMapper.parkingLotToDtoWithLevels(parkingLotRepository.save(parkingLot));
    }

    @Override
    public ParkingLot findById(Long parkingLotId) {
        return parkingLotRepository.findById(parkingLotId).orElseThrow(()
                -> new EntityNotFoundByIdException("ParkingLot was not found by id: " + parkingLotId));
    }

    @Override
    public ParkingSlot getAvailableSlot(Long parkingLotId, VehicleType vehicleType) {
        ParkingLot parkingLot = findById(parkingLotId);
        return parkingLot.getLevels().stream()
                .flatMap(l -> l.getParkingSlots().stream()
                        .filter(p -> !p.isOccupied())
                        .filter(s -> s.getType().canAccommodate(vehicleType))).findFirst()
                .orElseThrow(() -> new AvailableSlotNotFoundException(
                        String.format("Available slot not found in this parking:" +
                        " %s for this vehicleType: %s", parkingLotId, vehicleType)));
    }

    @Override
    public List<ParkingSlot> geOccupiedParkingSlots(Long parkingLotId) {
        return findById(parkingLotId).getLevels().stream()
                .flatMap(l -> l.getParkingSlots().stream()
                        .filter(s -> !s.isOccupied())).collect(Collectors.toList());
    }

    private Long calculateNextLevelNumber(ParkingLot parkingLot) {
        if (parkingLot.getLevels().isEmpty()) {
            return 1L;
        }

        long maxLevel = parkingLot.getLevels().stream()
                .mapToLong(level -> level.getId().getLevelNumber())
                .max()
                .orElse(0L);

        return maxLevel + 1;
    }

}
