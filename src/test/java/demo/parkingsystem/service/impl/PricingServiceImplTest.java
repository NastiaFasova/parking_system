package demo.parkingsystem.service.impl;

import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.strategy.HourlyPricingStrategy;
import demo.parkingsystem.strategy.PricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {

    @Mock
    private PricingStrategy pricingStrategy = new HourlyPricingStrategy();

    private PricingServiceImpl pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingServiceImpl(pricingStrategy);
    }

    @Test
    @DisplayName("Should calculate parking fee correctly")
    void shouldCalculateParkingFeeCorrectly() {
        // Given
        Duration duration = Duration.ofHours(3);
        BigDecimal expectedFee = BigDecimal.valueOf(6.0);

        when(pricingStrategy.calculatePrice(VehicleType.CAR, duration))
                .thenReturn(expectedFee);

        // When
        BigDecimal result = pricingService.calculateParkingFee(VehicleType.CAR, duration);

        // Then
        assertThat(result).isEqualTo(expectedFee);
        verify(pricingStrategy).calculatePrice(VehicleType.CAR, duration);
    }
}
