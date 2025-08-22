package demo.parkingsystem.controller;

import demo.parkingsystem.dto.parkingSlot.AvailableParkingSlotDto;
import demo.parkingsystem.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetAvailableSlotsController {
    private final ParkingLotService parkingLotService;

    @GetMapping("/{parking-id}/available-slots")
    public ResponseEntity<List<AvailableParkingSlotDto>> geAvailableParkingSlots(@PathVariable("parking-id") Long parkingLotId) {
        return ResponseEntity.ok(parkingLotService.geAvailableParkingSlots(parkingLotId));
    }

}
