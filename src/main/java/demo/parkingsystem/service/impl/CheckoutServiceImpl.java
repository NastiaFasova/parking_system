package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.checkout.CheckoutRequestDto;
import demo.parkingsystem.dto.parkingFee.ParkingFeeDto;
import demo.parkingsystem.mapper.ParkingFeeMapper;
import demo.parkingsystem.exceptions.ActiveTicketsAbsentException;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.model.ParkingLot;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.repository.ParkingLotRepository;
import demo.parkingsystem.repository.VehicleRepository;
import demo.parkingsystem.service.CheckoutService;
import demo.parkingsystem.service.ParkingFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingFeeService parkingFeeService;
    private final ParkingFeeMapper parkingFeeMapper;

    @Override
    public ParkingFeeDto checkOut(CheckoutRequestDto checkoutRequestDto) {
        String licensePlate = checkoutRequestDto.getLicensePlate();
        Long parkingLotId = checkoutRequestDto.getParkingLotId();
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(() ->
                new EntityNotFoundByIdException("Vehicle is not found: " + licensePlate));
        ParkingTicket parkingTicket = vehicle.getParkingTickets().stream()
                .filter(p -> p.getParkingSlot().isOccupied())
                .filter(p -> p.getExitDate() == null)
                .findFirst().orElseThrow(() -> new ActiveTicketsAbsentException(
                        String.format("Active tickets for this vehicle: %s on this parking: %s", licensePlate, parkingLotId)));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(()
                -> new EntityNotFoundByIdException("Parking is not found by this id: " + parkingLotId));
        ParkingSlot parkingSlot = parkingLot.getLevels().stream()
                .flatMap(l -> l.getParkingSlots().stream()
                        .filter(s -> s.equals(parkingTicket.getParkingSlot()))).findFirst().orElseThrow(() ->
                        new RuntimeException("This vehicle is parked in another parking lot!!!"));
        parkingSlot.setOccupied(false);
        parkingTicket.setExitDate(LocalDateTime.now());

        return parkingFeeMapper.toDto(parkingFeeService.create(parkingTicket, vehicle.getType(),
                Duration.between(parkingTicket.getEntryDate(), parkingTicket.getExitDate())));
    }

    @Override
    public String getFormattedDuration(LocalDateTime entryDate, LocalDateTime exitDate) {
        if (entryDate == null || exitDate == null) {
            return "0h 0m";
        }
        Duration duration = Duration.between(entryDate, exitDate);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%dh %dm", hours, minutes);
    }
}
