package demo.parkingsystem.service.impl;

import demo.parkingsystem.dto.level.LevelWithSlotsDto;
import demo.parkingsystem.exceptions.EntityNotFoundByIdException;
import demo.parkingsystem.mapper.LevelMapper;
import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.ParkingSlot;
import demo.parkingsystem.model.embedded.LevelId;
import demo.parkingsystem.model.embedded.ParkingSlotId;
import demo.parkingsystem.model.enums.ParkingSlotType;
import demo.parkingsystem.repository.LevelRepository;
import demo.parkingsystem.service.ParkingSlotFactoryService;
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
class LevelServiceImplTest {

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private LevelMapper levelMapper;

    @Mock
    private ParkingSlotFactoryService parkingSlotFactoryService;

    @InjectMocks
    private LevelServiceImpl levelService;

    private Level level;
    private ParkingSlot parkingSlot;
    private LevelWithSlotsDto expectedDto;

    @BeforeEach
    void setUp() {
        LevelId levelId = new LevelId(1L, 1L);
        level = new Level();
        level.setId(levelId);

        ParkingSlotId slotId = new ParkingSlotId(1L, 1L, 1L);
        parkingSlot = new ParkingSlot();
        parkingSlot.setId(slotId);
        parkingSlot.setType(ParkingSlotType.COMPACT);

        expectedDto = new LevelWithSlotsDto();
        expectedDto.setParkingLotId(1L);
        expectedDto.setLevelNumber(1L);
    }

    @Test
    @DisplayName("Should add parking slot successfully")
    void shouldAddParkingSlotSuccessfully() {
        // Given
        when(levelRepository.findById(any(LevelId.class))).thenReturn(Optional.of(level));
        when(parkingSlotFactoryService.createParkingSlot(ParkingSlotType.COMPACT, level))
                .thenReturn(parkingSlot);
        when(levelRepository.save(level)).thenReturn(level);
        when(levelMapper.toDto(level)).thenReturn(expectedDto);

        // When
        LevelWithSlotsDto result = levelService.addParkingSlot(1L, 1L, ParkingSlotType.COMPACT);

        // Then
        assertThat(result).isNotNull();

        verify(parkingSlotFactoryService).createParkingSlot(ParkingSlotType.COMPACT, level);
        verify(levelRepository).save(level);
    }

    @Test
    @DisplayName("Should remove parking slot successfully")
    void shouldRemoveParkingSlotSuccessfully() {
        // Given
        level.getParkingSlots().add(parkingSlot);

        when(levelRepository.findById(any(LevelId.class))).thenReturn(Optional.of(level));
        when(levelRepository.save(level)).thenReturn(level);
        when(levelMapper.toDto(level)).thenReturn(expectedDto);

        // When
        LevelWithSlotsDto result = levelService.removeParkingSlot(1L, 1L, 1L);

        // Then
        assertThat(result).isNotNull();

        verify(levelRepository).save(level);
    }

    @Test
    @DisplayName("Should throw exception when level not found")
    void shouldThrowExceptionWhenLevelNotFound() {
        // Given
        when(levelRepository.findById(any(LevelId.class))).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> levelService.findById(new LevelId(1L, 1L)))
                .isInstanceOf(EntityNotFoundByIdException.class)
                .hasMessageContaining("Level was not found by id");
    }
}

