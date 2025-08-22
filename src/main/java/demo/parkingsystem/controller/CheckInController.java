package demo.parkingsystem.controller;

import demo.parkingsystem.dto.checkIn.CheckInRequestDto;
import demo.parkingsystem.dto.parkingTicket.ParkingTicketDto;
import demo.parkingsystem.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<ParkingTicketDto> checkIn(@RequestBody CheckInRequestDto checkInDto) {
        return ResponseEntity.ok(checkInService.checkIn(checkInDto));
    }
}
