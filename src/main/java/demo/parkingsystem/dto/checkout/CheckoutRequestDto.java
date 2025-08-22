package demo.parkingsystem.dto.checkout;

import lombok.Data;

@Data
public class CheckoutRequestDto {
    private String licensePlate;
    private Long parkingLotId;
}
