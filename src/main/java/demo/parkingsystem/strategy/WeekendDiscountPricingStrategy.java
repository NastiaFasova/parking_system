package demo.parkingsystem.strategy;

import demo.parkingsystem.model.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;

@Component
public class WeekendDiscountPricingStrategy implements PricingStrategy {

    private static final BigDecimal WEEKEND_DISCOUNT = BigDecimal.valueOf(0.8);
    private final PricingStrategy basePricing = new HourlyPricingStrategy();

    @Override
    public BigDecimal calculatePrice(VehicleType vehicleType, Duration parkingDuration) {
        BigDecimal basePrice = basePricing.calculatePrice(vehicleType, parkingDuration);
        return basePrice.multiply(WEEKEND_DISCOUNT);
    }

    @Override
    public String getStrategyName() {
        return "WEEKEND_DISCOUNT";
    }
}
