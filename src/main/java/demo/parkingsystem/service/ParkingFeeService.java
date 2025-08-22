package demo.parkingsystem.service;

import demo.parkingsystem.model.ParkingFee;
import demo.parkingsystem.model.ParkingTicket;
import demo.parkingsystem.model.enums.VehicleType;

import java.time.Duration;

public interface ParkingFeeService {
    ParkingFee create(ParkingTicket parkingTicket, VehicleType vehicleType, Duration duration);
}
