package demo.parkingsystem.model.enums;

public enum ParkingSlotType {
    COMPACT, LARGE, MOTORCYCLE, HANDICAPPED;

    public boolean canAccommodate(VehicleType vehicleType) {
        return vehicleType.getPreferredSlotTypes().contains(this);
    }
}
