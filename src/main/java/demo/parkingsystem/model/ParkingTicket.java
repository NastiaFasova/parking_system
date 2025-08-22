package demo.parkingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;
    @OneToOne
    private ParkingSlot parkingSlot;
    @ManyToOne
    private Vehicle vehicle;

    public ParkingTicket(LocalDateTime entryDate, ParkingSlot parkingSlot, Vehicle vehicle) {
        this.entryDate = entryDate;
        this.parkingSlot = parkingSlot;
        this.vehicle = vehicle;
    }
}
