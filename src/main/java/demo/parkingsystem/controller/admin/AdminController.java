package demo.parkingsystem.controller.admin;

import demo.parkingsystem.dto.level.LevelWithSlotsDto;
import demo.parkingsystem.dto.MessageDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotResponseDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotWithLevelsDto;
import demo.parkingsystem.dto.parkingSlot.AddParkingSlotDto;
import demo.parkingsystem.dto.parkingSlot.ParkingSlotDto;
import demo.parkingsystem.dto.parkingSlot.UpdateSlotStatusRequest;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.service.LevelService;
import demo.parkingsystem.service.ParkingLotService;
import demo.parkingsystem.service.ParkingSlotService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ParkingLotService parkingLotService;
    private final LevelService levelService;
    private final ParkingSlotService parkingSlotService;

    @PostMapping("/")
    public ResponseEntity<ParkingLotResponseDto> create(@RequestBody ParkingLotDto createParkingLotRequestDto) {
        return ResponseEntity.ok(parkingLotService.create(createParkingLotRequestDto));
    }

    @DeleteMapping("/{parking-id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("parking-id") Long parkingLotId) {
        parkingLotService.remove(parkingLotId);
        return ResponseEntity.ok(new MessageDto("Parking Lot was successfully removed"));
    }

    @GetMapping("/{parking-id}/add-level")
    public ResponseEntity<ParkingLotWithLevelsDto> addLevel(@PathVariable("parking-id") Long parkingLotId) {
        return ResponseEntity.ok(parkingLotService.addLevel(parkingLotId));
    }

    @DeleteMapping("/{parking-id}/{level-id}")
    public ResponseEntity<ParkingLotWithLevelsDto> removeLevel(@PathVariable("parking-id") Long parkingLotId,
                                                     @PathVariable("level-id") Long levelId) {
        return ResponseEntity.ok(parkingLotService.removeLevel(parkingLotId, levelId));
    }

    @PostMapping("/{parking-id}/{level-id}/add-slot")
    public ResponseEntity<LevelWithSlotsDto> addParkingSlot(@PathVariable("parking-id") Long parkingLotId,
                                                            @PathVariable("level-id") Long levelId,
                                                            @RequestBody AddParkingSlotDto parkingSlotDto) {
        return ResponseEntity.ok(levelService.addParkingSlot(parkingLotId, levelId,
                ParkingSlotType.valueOf(parkingSlotDto.getParkingSlotType())));
    }

    @DeleteMapping("/{parking-id}/{level-id}/{slot-id}")
    public ResponseEntity<LevelWithSlotsDto> removeParkingSlot(@PathVariable("parking-id") Long parkingLotId,
                                                               @PathVariable("level-id") Long levelId,
                                                               @PathVariable("slot-id") Long slotId) {
        return ResponseEntity.ok(levelService.removeParkingSlot(parkingLotId, levelId, slotId));
    }

    @PatchMapping("/{parking-id}/{level-id}/{slot-id}/status")
    public ResponseEntity<ParkingSlotDto> changeStatus(@PathVariable("parking-id") Long parkingLotId,
                                                       @PathVariable("level-id") Long levelId,
                                                       @PathVariable("slot-id") Long slotId,
                                                       @RequestBody UpdateSlotStatusRequest updateSlotStatusRequest) {
        return ResponseEntity.ok(parkingSlotService.isOccupied(parkingLotId, levelId, slotId,
                updateSlotStatusRequest.isOccupied()));
    }
}
