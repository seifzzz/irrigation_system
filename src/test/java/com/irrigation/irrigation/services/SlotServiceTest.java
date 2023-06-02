package com.irrigation.irrigation.services;


import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.model.slot;
import com.irrigation.irrigation.repositories.SlotRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;
    @InjectMocks
    private SlotService slotService;

    @Test
    public void save() {

        slot slot = getSlot();
        when(slotRepository.save(any(slot.class))).thenReturn(slot);

        final var actual = slotService.save(new slot());

        assertThat(actual).usingRecursiveComparison().isEqualTo(slot);
        verify(slotRepository, times(1)).save(any(slot.class));
        verifyNoMoreInteractions(slotRepository);
    }

    //When Testing this
    @Test
    public void CreateSlot() {

        slot slot = getSlot();
        land land = new land("londan",30,"onion");
        land.setId(1L);

        final var actual = slotService.createSlot(land);

        assertThat(actual).usingRecursiveComparison().isEqualTo(slot);

    }

    @Test
    void FindAll() {
        // Arrange
        when(slotRepository.findAll()).thenReturn(List.of(new slot()));

        // Act & Assert
        assertThat(slotService.findAll()).hasSize(1);
        verify(slotRepository, times(1)).findAll();
        verifyNoMoreInteractions(slotRepository);
    }
    @Test
    public void FindByID() {

        slot slot = getSlot();
        when(slotRepository.findById(anyLong())).thenReturn(Optional.of(slot));

        final Optional<slot> actual = slotService.findById((long) 3 );

        assertThat(actual).hasValue(slot);
        verify(slotRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(slotRepository);
    }

    private slot getSlot() {
        slot slot = new slot();
        slot.setLand_id(1L);
        slot.setTime(5);
        slot.setWater(3);
        return slot;
    }

}
