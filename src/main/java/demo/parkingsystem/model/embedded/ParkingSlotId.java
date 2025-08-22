package demo.parkingsystem.model.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParkingSlotId implements Serializable {

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "level_number")
    private Long levelNumber;

    @Column(name = "slot_number")
    private Long slotNumber;

    public ParkingSlotId(LevelId levelId, Long slotNumber) {
        this.parkingLotId = levelId.getParkingLotId();
        this.levelNumber = levelId.getLevelNumber();
        this.slotNumber = slotNumber;
    }

    public LevelId getLevelId() {
        return new LevelId(parkingLotId, levelNumber);
    }

    @Override
    public String toString() {
        return String.format("ParkingLot: %d; Level: %d; Slot: %d", parkingLotId, levelNumber, slotNumber);
    }
}
