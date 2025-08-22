package demo.parkingsystem.controller;

import demo.parkingsystem.dto.checkout.CheckoutRequestDto;
import demo.parkingsystem.dto.parkingFee.ParkingFeeDto;
import demo.parkingsystem.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/check-out")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<ParkingFeeDto> checkIn(@RequestBody CheckoutRequestDto checkoutRequestDto) {
        return ResponseEntity.ok(checkoutService.checkOut(checkoutRequestDto));
    }
}
