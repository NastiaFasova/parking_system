package demo.parkingsystem.model.enums;

import java.util.List;

public enum VehicleType {
    CAR(2), MOTORCYCLE(1), TRUCK(3);

    private final Integer ratePerHour;

    VehicleType(Integer ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public Integer getRatePerHour() {
        return ratePerHour;
    }

    public List<ParkingSlotType> getPreferredSlotTypes() {
        return switch (this) {
            case MOTORCYCLE -> List.of(
                    ParkingSlotType.MOTORCYCLE,
                    ParkingSlotType.COMPACT,
                    ParkingSlotType.LARGE,
                    ParkingSlotType.HANDICAPPED
            );
            case CAR -> List.of(
                    ParkingSlotType.COMPACT,
                    ParkingSlotType.LARGE,
                    ParkingSlotType.HANDICAPPED
            );
            case TRUCK -> List.of(
                    ParkingSlotType.LARGE,
                    ParkingSlotType.HANDICAPPED
            );
        };
    }
}
