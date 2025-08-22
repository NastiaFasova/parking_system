package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.vehicle.VehicleDto;
import demo.parkingsystem.model.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleDto toDto(Vehicle vehicle);
}
