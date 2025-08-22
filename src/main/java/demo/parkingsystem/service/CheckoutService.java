package demo.parkingsystem.service;

import demo.parkingsystem.dto.checkout.CheckoutRequestDto;
import demo.parkingsystem.dto.parkingFee.ParkingFeeDto;

import java.time.LocalDateTime;

public interface CheckoutService {
    ParkingFeeDto checkOut(CheckoutRequestDto checkoutRequestDto);
    String getFormattedDuration(LocalDateTime entryDate, LocalDateTime exitDate);
}
