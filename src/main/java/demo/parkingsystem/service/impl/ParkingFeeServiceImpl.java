package demo.parkingsystem.service.impl;

import demo.parkingsystem.model.ParkingFee;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.service.ParkingFeeService;
import demo.parkingsystem.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ParkingFeeServiceImpl implements ParkingFeeService {
    private final PricingService pricingService;

    @Override
    public ParkingFee create(ParkingTicket parkingTicket, VehicleType vehicleType, Duration duration) {
        BigDecimal fee = pricingService.calculateParkingFee(vehicleType, duration);
        return new ParkingFee(fee, parkingTicket, duration);
    }
}
