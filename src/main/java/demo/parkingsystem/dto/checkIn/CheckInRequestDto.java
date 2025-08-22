package demo.parkingsystem.dto.checkIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInRequestDto {
    private String licencePlate;
    private String vehicleType;
    private Long parkingLotId;
}
