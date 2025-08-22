package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.parkingLot.ParkingLotDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotResponseDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotWithLevelsDto;
import demo.parkingsystem.model.ParkingLot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingLotMapper {
    ParkingLot createParkingLotDtoToParkingDto(ParkingLotDto dto);
    ParkingLotDto parkingLotToParkingLotDto(ParkingLot parkingLot);
    ParkingLotResponseDto parkingLotToParkingLotResponseDto(ParkingLot parkingLot);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "levels", expression = "java(parkingLot.getLevels() != null ? parkingLot.getLevels().size() : 0)")
    ParkingLotWithLevelsDto parkingLotToDtoWithLevels(ParkingLot parkingLot);
}
