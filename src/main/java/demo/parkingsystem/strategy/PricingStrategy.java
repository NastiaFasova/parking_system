package demo.parkingsystem.strategy;

import demo.parkingsystem.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.Duration;

public interface PricingStrategy {
    BigDecimal calculatePrice(VehicleType vehicleType, Duration parkingDuration);
    String getStrategyName();
}
