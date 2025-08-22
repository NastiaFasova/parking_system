package demo.parkingsystem.service.impl;

import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.service.PricingService;
import demo.parkingsystem.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class PricingServiceImpl implements PricingService {

    private final PricingStrategy pricingStrategy;

    public PricingServiceImpl(@Qualifier("hourlyPricingStrategy") PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public BigDecimal calculateParkingFee(VehicleType vehicleType, Duration parkingDuration) {
        return pricingStrategy.calculatePrice(vehicleType, parkingDuration);
    }
}
