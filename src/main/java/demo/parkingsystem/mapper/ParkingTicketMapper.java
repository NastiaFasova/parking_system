package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.parkingTicket.ParkingTicketDto;
import demo.parkingsystem.model.ParkingTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParkingSlotMapper.class, VehicleMapper.class})
public interface ParkingTicketMapper {
    @Mapping(target = "parkingSlotNumber", source = "parkingSlot.id.slotNumber")
    @Mapping(target = "entryDate", source = "entryDate")
    @Mapping(target = "levelNumber", source = "parkingSlot.id.levelNumber")
    @Mapping(target = "parkingLotName", source = "parkingSlot.level.parkingLot.name")
    @Mapping(target = "parkingSlotType", source = "parkingSlot.type")
    @Mapping(target = "licensePlate", source = "vehicle.licensePlate")
    @Mapping(target = "vehicleType", source = "vehicle.type")
    ParkingTicketDto toDto(ParkingTicket parkingTicket);
}
