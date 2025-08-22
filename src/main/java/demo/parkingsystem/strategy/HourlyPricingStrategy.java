package demo.parkingsystem.strategy;

import demo.parkingsystem.model.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;

@Component
public class HourlyPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(VehicleType vehicleType, Duration parkingDuration) {
        long hours = Math.max(1, parkingDuration.toHours());
        BigDecimal hourlyRate = BigDecimal.valueOf(vehicleType.getRatePerHour());
        return hourlyRate.multiply(BigDecimal.valueOf(hours));
    }

    @Override
    public String getStrategyName() {
        return "HOURLY";
    }
}
