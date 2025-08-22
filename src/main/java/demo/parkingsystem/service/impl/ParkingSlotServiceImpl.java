package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.parkingSlot.ParkingSlotDto;
import demo.parkingsystem.mapper.ParkingSlotMapper;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.repository.ParkingSlotRepository;
import demo.parkingsystem.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSlotServiceImpl implements ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingSlotMapper parkingSlotMapper;

    @Override
    public ParkingSlot findById(ParkingSlotId parkingSlotId) {
        return parkingSlotRepository.findById(parkingSlotId)
                .orElseThrow(() -> new EntityNotFoundByIdException("ParkingSlot was not found by id: " + parkingSlotId));
    }

    @Override
    public ParkingSlot save(ParkingSlot parkingSlot) {
        return parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public ParkingSlotDto isOccupied(Long parkingLotId, Long levelId, Long parkingSlotId, boolean isOccupied) {
        ParkingSlotId parkingSlotIdObj = new ParkingSlotId(parkingLotId, levelId, parkingSlotId);
        ParkingSlot parkingSlot = findById(parkingSlotIdObj);
        parkingSlot.setOccupied(isOccupied);
        return parkingSlotMapper.toDto(parkingSlotRepository.save(parkingSlot));
    }
}
