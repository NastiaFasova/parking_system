package demo.parkingsystem.service.impl;

import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.repository.ParkingTicketRepository;
import demo.parkingsystem.service.ParkingTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingTicketServiceImpl implements ParkingTicketService {
    private final ParkingTicketRepository parkingTicketRepository;

    @Override
    public ParkingTicket create(ParkingTicket parkingTicket) {
        return parkingTicketRepository.save(parkingTicket);
    }
}
