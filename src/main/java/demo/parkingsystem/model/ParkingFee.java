package demo.parkingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal fee;
    private Duration totalDuration;
    @OneToOne
    private ParkingTicket parkingTicket;

    public ParkingFee(BigDecimal fee, ParkingTicket parkingTicket, Duration duration) {
        this.fee = fee;
        this.parkingTicket = parkingTicket;
        this.totalDuration = duration;
    }
}
