package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.parkingSlot.AvailableParkingSlotDto;
import demo.parkingsystem.dto.parkingSlot.ParkingSlotDto;
import demo.parkingsystem.model.ParkingSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingSlotMapper {
    @Mapping(target = "parkingSlotNumber", source = "id.slotNumber")
    ParkingSlotDto toDto(ParkingSlot parkingSlot);

    @Mapping(target = "parkingSlotNumber", source = "id.slotNumber")
    @Mapping(target = "levelId", source = "level.id.levelNumber")
    AvailableParkingSlotDto toAvailableSlotsDto(ParkingSlot parkingSlot);
}
