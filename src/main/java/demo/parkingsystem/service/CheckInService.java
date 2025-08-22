package demo.parkingsystem.service;

import demo.parkingsystem.dto.checkIn.CheckInRequestDto;
import demo.parkingsystem.dto.parkingTicket.ParkingTicketDto;

public interface CheckInService {
    ParkingTicketDto checkIn(CheckInRequestDto checkInDto);
}
