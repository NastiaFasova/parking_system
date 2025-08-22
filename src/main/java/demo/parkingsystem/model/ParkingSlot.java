package demo.parkingsystem.model;

import demo.parkingsystem.model.enums.ParkingSlotType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ParkingSlot {
        @EmbeddedId
        private ParkingSlotId id;

        @Enumerated(EnumType.STRING)
        private ParkingSlotType type;

        private boolean isOccupied;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumns({
                @JoinColumn(name = "parking_lot_id", referencedColumnName = "parking_lot_id", insertable = false, updatable = false),
                @JoinColumn(name = "level_number", referencedColumnName = "level_number", insertable = false, updatable = false)
        })
        private Level level;

        public ParkingSlot(Level level, ParkingSlotType type) {
            this.level = level;
            this.type = type;
        }

        public ParkingSlot(ParkingSlotId id, ParkingSlotType type, Level level, boolean isOccupied) {
            this.id = id;
            this.type = type;
            this.level = level;
            this.isOccupied = isOccupied;
        }

        @Override
        public String toString() {
            return String.format(" type: %s; isOccupied: %s", type, isOccupied);
        }
    }
