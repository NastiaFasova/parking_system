package demo.parkingsystem.repository;

import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, ParkingSlotId> {
}
