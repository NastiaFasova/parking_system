package demo.parkingsystem.model;

import demo.parkingsystem.model.embedded.LevelId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Level {
    @EmbeddedId
    private LevelId id;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_lot_id", insertable = false, updatable = false)
    private ParkingLot parkingLot;

    public Level(LevelId id, ParkingLot parkingLot) {
        this.id = id;
        this.parkingLot = parkingLot;
    }
}
