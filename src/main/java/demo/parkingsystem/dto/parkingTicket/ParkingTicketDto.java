package demo.parkingsystem.dto.parkingTicket;

import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.model.enums.VehicleType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingTicketDto {
    private LocalDateTime entryDate;
    private Long parkingSlotNumber;
    private Long levelNumber;
    private String parkingLotName;
    private ParkingSlotType parkingSlotType;
    private String licensePlate;
    private VehicleType vehicleType;
}
