package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.checkout.CheckoutRequestDto;
import demo.parkingsystem.dto.parkingFee.ParkingFeeDto;
import demo.parkingsystem.exceptions.ActiveTicketsAbsentException;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.mapper.ParkingFeeMapper;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingFee;
import demo.parkingsystem.model.ParkingLot;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.repository.ParkingLotRepository;
import demo.parkingsystem.repository.VehicleRepository;
import demo.parkingsystem.service.ParkingFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingFeeService parkingFeeService;

    @Mock
    private ParkingFeeMapper parkingFeeMapper;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private CheckoutRequestDto checkoutRequest;
    private Vehicle vehicle;
    private ParkingLot parkingLot;
    private Level level;
    private ParkingSlot parkingSlot;
    private ParkingTicket parkingTicket;
    private ParkingFee parkingFee;
    private ParkingFeeDto expectedFeeDto;

    @BeforeEach
    void setUp() {
        checkoutRequest = new CheckoutRequestDto();
        checkoutRequest.setLicensePlate("ABC123");
        checkoutRequest.setParkingLotId(1L);

        vehicle = new Vehicle(VehicleType.CAR, "ABC123");

        ParkingSlotId slotId = new ParkingSlotId(1L, 1L, 1L);
        parkingSlot = new ParkingSlot();
        parkingSlot.setId(slotId);
        parkingSlot.setType(ParkingSlotType.COMPACT);
        parkingSlot.setOccupied(true);

        parkingTicket = new ParkingTicket(LocalDateTime.now().minusHours(2), parkingSlot, vehicle);
        parkingTicket.setExitDate(null);
        vehicle.getParkingTickets().add(parkingTicket);

        LevelId levelId = new LevelId(1L, 1L);
        level = new Level();
        level.setId(levelId);
        level.getParkingSlots().add(parkingSlot);

        parkingLot = new ParkingLot("Test Parking");
        parkingLot.setId(1L);
        parkingLot.getLevels().add(level);

        parkingFee = new ParkingFee(BigDecimal.valueOf(4.0), parkingTicket, Duration.ofHours(2));

        expectedFeeDto = new ParkingFeeDto();
        expectedFeeDto.setFee(4.0);
    }

    @Test
    @DisplayName("Should successfully check out vehicle")
    void shouldSuccessfullyCheckOutVehicle() {
        // Given
        when(vehicleRepository.findById("ABC123")).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(parkingFeeService.create(eq(parkingTicket), eq(VehicleType.CAR), any(Duration.class)))
                .thenReturn(parkingFee);
        when(parkingFeeMapper.toDto(parkingFee)).thenReturn(expectedFeeDto);

        // When
        ParkingFeeDto result = checkoutService.checkOut(checkoutRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFee()).isEqualTo(4.0);
        assertThat(parkingSlot.isOccupied()).isFalse();
        assertThat(parkingTicket.getExitDate()).isNotNull();

        verify(vehicleRepository).findById("ABC123");
        verify(parkingLotRepository).findById(1L);
        verify(parkingFeeService).create(eq(parkingTicket), eq(VehicleType.CAR), any(Duration.class));
    }

    @Test
    @DisplayName("Should throw exception when vehicle not found")
    void shouldThrowExceptionWhenVehicleNotFound() {
        // Given
        when(vehicleRepository.findById("ABC123")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> checkoutService.checkOut(checkoutRequest))
                .isInstanceOf(EntityNotFoundByIdException.class)
                .hasMessage("Vehicle is not found: ABC123");
    }

    @Test
    @DisplayName("Should throw exception when no active ticket found")
    void shouldThrowExceptionWhenNoActiveTicketFound() {
        // Given
        vehicle.getParkingTickets().clear(); // No tickets
        when(vehicleRepository.findById("ABC123")).thenReturn(Optional.of(vehicle));

        // When & Then
        assertThatThrownBy(() -> checkoutService.checkOut(checkoutRequest))
                .isInstanceOf(ActiveTicketsAbsentException.class);
    }

    @Test
    @DisplayName("Should format duration correctly")
    void shouldFormatDurationCorrectly() {
        // Given
        LocalDateTime entryDate = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime exitDate = LocalDateTime.of(2024, 1, 1, 13, 30);

        // When
        String formatted = checkoutService.getFormattedDuration(entryDate, exitDate);

        // Then
        assertThat(formatted).isEqualTo("3h 30m");
    }

    @Test
    @DisplayName("Should handle null dates in duration formatting")
    void shouldHandleNullDatesInDurationFormatting() {
        // When
        String formatted = checkoutService.getFormattedDuration(null, null);

        // Then
        assertThat(formatted).isEqualTo("0h 0m");
    }
}
