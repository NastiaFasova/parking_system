package demo.parkingsystem.repository;

import demo.parkingsystem.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
}
