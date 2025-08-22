package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.level.LevelWithSlotsDto;
import demo.parkingsystem.model.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ParkingSlotMapper.class)
public interface LevelMapper {
    @Mapping(target = "parkingLotId", source = "id.parkingLotId")
    @Mapping(target = "levelNumber", source = "id.levelNumber")
    @Mapping(target = "parkingSlots", source = "parkingSlots")
    LevelWithSlotsDto toDto(Level level);
}
