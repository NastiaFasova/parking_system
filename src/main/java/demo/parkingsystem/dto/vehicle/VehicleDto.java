package demo.parkingsystem.dto.vehicle;

import demo.parkingsystem.model.enums.VehicleType;
import lombok.Data;

@Data
public class VehicleDto {
    private String licensePlate;
    private VehicleType type;
}
