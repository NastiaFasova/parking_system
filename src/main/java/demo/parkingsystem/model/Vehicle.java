package demo.parkingsystem.model;

import demo.parkingsystem.model.enums.VehicleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.PERSIST})
    private List<ParkingTicket> parkingTickets = new ArrayList<>();

    public Vehicle(VehicleType type, String licensePlate) {
        this.type = type;
        this.licensePlate = licensePlate;
    }
}
