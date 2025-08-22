package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.checkIn.CheckInRequestDto;
import demo.parkingsystem.dto.parkingTicket.ParkingTicketDto;
import demo.parkingsystem.mapper.ParkingTicketMapper;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.Vehicle;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.service.CheckInService;
import demo.parkingsystem.service.ParkingLotService;
import demo.parkingsystem.service.ParkingSlotService;
import demo.parkingsystem.service.ParkingTicketService;
import demo.parkingsystem.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {
    private final VehicleService vehicleService;
    private final ParkingLotService parkingLotService;
    private final ParkingTicketMapper parkingTicketMapper;

    private final ParkingSlotService parkingSlotService;
    private final ParkingTicketService parkingTicketService;


    @Override
    public ParkingTicketDto checkIn(CheckInRequestDto checkInDto) {
        VehicleType vehicleType = VehicleType.valueOf(checkInDto.getVehicleType());
        Vehicle vehicle = vehicleService.getVehicleForCheckIn(checkInDto.getLicencePlate(), vehicleType);

        ParkingSlot parkingSlot = parkingLotService.getAvailableSlot(checkInDto.getParkingLotId(), vehicleType);
        parkingSlot.setOccupied(true);
        parkingSlotService.save(parkingSlot);

        ParkingTicket parkingTicket = new ParkingTicket(LocalDateTime.now(), parkingSlot, vehicle);
        return parkingTicketMapper.toDto(parkingTicketService.create(parkingTicket));
    }
}
