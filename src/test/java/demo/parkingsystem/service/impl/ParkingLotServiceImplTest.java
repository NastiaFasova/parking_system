package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.parkingLot.ParkingLotDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotResponseDto;
import demo.parkingsystem.dto.parkingLot.ParkingLotWithLevelsDto;
import demo.parkingsystem.exceptions.AvailableSlotNotFoundException;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.mapper.ParkingLotMapper;
import demo.parkingsystem.mapper.ParkingSlotMapper;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingLot;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.model.enums.VehicleType;
import demo.parkingsystem.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingLotMapper parkingLotMapper;

    @Mock
    private ParkingSlotMapper parkingSlotMapper;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLot parkingLot;
    private ParkingLotDto parkingLotDto;
    private Level level;
    private ParkingSlot availableSlot;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot("Test Parking");
        parkingLot.setId(1L);

        parkingLotDto = new ParkingLotDto();
        parkingLotDto.setName("Test Parking");

        LevelId levelId = new LevelId(1L, 1L);
        level = new Level();
        level.setId(levelId);
        parkingLot.getLevels().add(level);

        ParkingSlotId slotId = new ParkingSlotId(1L, 1L, 1L);
        availableSlot = new ParkingSlot();
        availableSlot.setId(slotId);
        availableSlot.setType(ParkingSlotType.COMPACT);
        availableSlot.setOccupied(false);
        level.getParkingSlots().add(availableSlot);
    }

    @Test
    @DisplayName("Should create parking lot successfully")
    void shouldCreateParkingLotSuccessfully() {
        // Given
        ParkingLotResponseDto expectedResponse = new ParkingLotResponseDto();
        expectedResponse.setId(1L);
        expectedResponse.setName("Test Parking");

        when(parkingLotMapper.createParkingLotDtoToParkingDto(parkingLotDto))
                .thenReturn(parkingLot);
        when(parkingLotRepository.save(parkingLot)).thenReturn(parkingLot);
        when(parkingLotMapper.parkingLotToParkingLotResponseDto(parkingLot))
                .thenReturn(expectedResponse);

        // When
        ParkingLotResponseDto result = parkingLotService.create(parkingLotDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Parking");

        verify(parkingLotRepository).save(parkingLot);
    }

    @Test
    @DisplayName("Should find available slot for vehicle type")
    void shouldFindAvailableSlotForVehicleType() {
        // Given
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        // When
        ParkingSlot result = parkingLotService.getAvailableSlot(1L, VehicleType.CAR);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(ParkingSlotType.COMPACT);
        assertThat(result.isOccupied()).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when no available slot found")
    void shouldThrowExceptionWhenNoAvailableSlotFound() {
        // Given
        availableSlot.setOccupied(true); // Make slot occupied
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));

        // When & Then
        assertThatThrownBy(() -> parkingLotService.getAvailableSlot(1L, VehicleType.CAR))
                .isInstanceOf(AvailableSlotNotFoundException.class)
                .hasMessageContaining("Available slot not found");
    }

    @Test
    @DisplayName("Should throw exception when parking lot not found")
    void shouldThrowExceptionWhenParkingLotNotFound() {
        // Given
        when(parkingLotRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> parkingLotService.findById(999L))
                .isInstanceOf(EntityNotFoundByIdException.class)
                .hasMessage("ParkingLot was not found by id: 999");
    }

    @Test
    @DisplayName("Should add level successfully")
    void shouldAddLevelSuccessfully() {
        // Given
        ParkingLot emptyParkingLot = new ParkingLot("Empty Parking");
        emptyParkingLot.setId(1L);

        ParkingLotWithLevelsDto expectedDto = new ParkingLotWithLevelsDto();
        expectedDto.setName("Empty Parking");

        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(emptyParkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(emptyParkingLot);
        when(parkingLotMapper.parkingLotToDtoWithLevels(any(ParkingLot.class)))
                .thenReturn(expectedDto);

        // When
        ParkingLotWithLevelsDto result = parkingLotService.addLevel(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(emptyParkingLot.getLevels().getFirst().getId().getLevelNumber()).isEqualTo(1L);

        verify(parkingLotRepository).save(emptyParkingLot);
    }

    @Test
    @DisplayName("Should calculate next level number correctly")
    void shouldCalculateNextLevelNumberCorrectly() {
        // Given - parkingLot already has level 1
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        when(parkingLotMapper.parkingLotToDtoWithLevels(any(ParkingLot.class)))
                .thenReturn(new ParkingLotWithLevelsDto());

        // When
        parkingLotService.addLevel(1L);

        // Then
        assertThat(parkingLot.getLevels().get(1).getId().getLevelNumber()).isEqualTo(2L);
    }
}
