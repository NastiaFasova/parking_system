package demo.parkingsystem.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class LevelId implements Serializable {

    @Column(name = "parking_lot_id")
    private Long parkingLotId;

    @Column(name = "level_number")
    private Long levelNumber;

    @Override
    public String toString() {
        return String.format("ParkingLot: %d; Level: %d", parkingLotId, levelNumber);
    }
}
