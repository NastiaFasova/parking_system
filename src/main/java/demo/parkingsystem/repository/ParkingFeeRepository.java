package demo.parkingsystem.repository;

import demo.parkingsystem.model.ParkingFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingFeeRepository extends JpaRepository<ParkingFee, Long> {
}
