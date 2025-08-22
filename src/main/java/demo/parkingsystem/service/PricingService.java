package demo.parkingsystem.service;

import demo.parkingsystem.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.Duration;

public interface PricingService {
    BigDecimal calculateParkingFee(VehicleType vehicleType, Duration parkingDuration);
}
