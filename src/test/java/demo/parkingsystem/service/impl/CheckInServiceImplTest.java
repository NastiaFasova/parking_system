package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.checkIn.CheckInRequestDto;
import demo.parkingsystem.dto.parkingTicket.ParkingTicketDto;
import demo.parkingsystem.exceptions.AvailableSlotNotFoundException;
import demo.parkingsystem.mapper.ParkingTicketMapper;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.service.ParkingLotService;
import demo.parkingsystem.service.ParkingSlotService;
import demo.parkingsystem.service.ParkingTicketService;
import demo.parkingsystem.service.VehicleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInServiceImplTest {

    @Mock
    private VehicleService vehicleService;

    @Mock
    private ParkingLotService parkingLotService;

    @Mock
    private ParkingTicketMapper parkingTicketMapper;

    @Mock
    private ParkingSlotService parkingSlotService;

    @Mock
    private ParkingTicketService parkingTicketService;

    private CheckInServiceImpl checkInService;

    private CheckInRequestDto checkInRequest;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;
    private ParkingTicket parkingTicket;
    private ParkingTicketDto expectedDto;

    @BeforeEach
    void setUp() {
        // Manual injection instead of @InjectMocks
        checkInService = new CheckInServiceImpl(
                vehicleService,
                parkingLotService,
                parkingTicketMapper,
                parkingSlotService,
                parkingTicketService
        );

        checkInRequest = new CheckInRequestDto();
        checkInRequest.setLicencePlate("ABC123");
        checkInRequest.setVehicleType("CAR");
        checkInRequest.setParkingLotId(1L);

        vehicle = new Vehicle(VehicleType.CAR, "ABC123");

        ParkingSlotId slotId = new ParkingSlotId(1L, 1L, 1L);
        parkingSlot = new ParkingSlot();
        parkingSlot.setId(slotId);
        parkingSlot.setType(ParkingSlotType.COMPACT);
        parkingSlot.setOccupied(false);

        parkingTicket = new ParkingTicket(LocalDateTime.now(), parkingSlot, vehicle);

        expectedDto = new ParkingTicketDto();
        expectedDto.setLicensePlate("ABC123");
        expectedDto.setVehicleType(VehicleType.CAR);
    }

    @Test
    void shouldSuccessfullyCheckInVehicle() {
        // Given
        when(vehicleService.getVehicleForCheckIn("ABC123", VehicleType.CAR))
                .thenReturn(vehicle);
        when(parkingLotService.getAvailableSlot(1L, VehicleType.CAR))
                .thenReturn(parkingSlot);
        when(parkingSlotService.save(parkingSlot))
                .thenReturn(parkingSlot);
        when(parkingTicketService.create(any(ParkingTicket.class)))
                .thenReturn(parkingTicket);
        when(parkingTicketMapper.toDto(parkingTicket))
                .thenReturn(expectedDto);

        // When
        ParkingTicketDto result = checkInService.checkIn(checkInRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getLicensePlate()).isEqualTo("ABC123");
        assertThat(result.getVehicleType()).isEqualTo(VehicleType.CAR);

        verify(vehicleService).getVehicleForCheckIn("ABC123", VehicleType.CAR);
        verify(parkingLotService).getAvailableSlot(1L, VehicleType.CAR);
        verify(parkingSlotService).save(parkingSlot);
        verify(parkingTicketService).create(any(ParkingTicket.class));

        assertThat(parkingSlot.isOccupied()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenInvalidVehicleType() {
        // Given
        checkInRequest.setVehicleType("INVALID");

        // When & Then
        assertThatThrownBy(() -> checkInService.checkIn(checkInRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenNoAvailableSlot() {
        // Given
        when(vehicleService.getVehicleForCheckIn("ABC123", VehicleType.CAR))
                .thenReturn(vehicle);
        when(parkingLotService.getAvailableSlot(1L, VehicleType.CAR))
                .thenThrow(new AvailableSlotNotFoundException("No available slots"));

        // When & Then
        assertThatThrownBy(() -> checkInService.checkIn(checkInRequest))
                .isInstanceOf(AvailableSlotNotFoundException.class)
                .hasMessage("No available slots");
    }
}