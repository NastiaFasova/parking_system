package demo.parkingsystem.dto.parkingFee;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingFeeDto {
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    private String formattedDuration;
    private Double fee;
}
