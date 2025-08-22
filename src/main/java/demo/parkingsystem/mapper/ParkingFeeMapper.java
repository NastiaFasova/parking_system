package demo.parkingsystem.mapper;

import demo.parkingsystem.dto.parkingFee.ParkingFeeDto;
import demo.parkingsystem.model.ParkingFee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface ParkingFeeMapper {
    @Mapping(target = "entryDate", source = "parkingTicket.entryDate")
    @Mapping(target = "exitDate", source = "parkingTicket.exitDate")
    @Mapping(target = "formattedDuration", source = "totalDuration", qualifiedByName = "formatDuration")
    ParkingFeeDto toDto(ParkingFee parkingFee);

    @Named("formatDuration")
    default String formatDuration(Duration duration) {
        if (duration == null) {
            return "0h 0m";
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.toSeconds() % 3600;
        return String.format("%dh %dm %ds", hours, minutes, seconds);
    }

}
