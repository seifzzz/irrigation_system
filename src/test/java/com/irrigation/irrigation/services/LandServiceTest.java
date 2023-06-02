package com.irrigation.irrigation.services;

import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.repositories.LandRepository;
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

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LandServiceTest {
    @Mock
    private LandRepository landRepository;

   @InjectMocks
   private LandService landService;
    @Test
    public void Create() {

        land land = getLand("El-Maadi",30,"fruits") ;
        when(landRepository.save(any(land.class))).thenReturn(land);

        final var actual = landService.create(new land());

        assertThat(actual).usingRecursiveComparison().isEqualTo(land);
        verify(landRepository, times(1)).save(any(land.class));
        verifyNoMoreInteractions(landRepository);
    }

    @Test
    public void Update() {

        land land = getLand("londan",80,"fruits") ;
        when(landRepository.save(any(land.class))).thenReturn(land);

        final var actual = landService.update(new land());

        assertThat(actual).usingRecursiveComparison().isEqualTo(land);
        verify(landRepository, times(1)).save(any(land.class));
        verifyNoMoreInteractions(landRepository);
    }

    @Test
    public void FindByID() {

        land land = getLand("shrouk",80,"fruits") ;
        when(landRepository.findById(anyLong())).thenReturn(Optional.of(land));

        final Optional<land> actual = landService.findById(3L);

        assertThat(actual).usingRecursiveComparison().isEqualTo(Optional.of(land));
        verify(landRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(landRepository);
    }
    @Test
    void FindAll() {
        // Arrange
        when(landRepository.findAll()).thenReturn(List.of(new land()));

        // Act & Assert
        assertThat(landService.findAll()).hasSize(1);
        verify(landRepository, times(1)).findAll();
        verifyNoMoreInteractions(landRepository);
    }
    @Test
    void deleteById() {
        // Arrange
        doNothing().when(landRepository).deleteById(anyLong());

        // Act & Assert
        landService.delete((long)1);
        verify(landRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(landRepository);
    }

    private land getLand(String name,int size,String crop) {
        land land = new land();
        land.setId(3L);
        land.setName(name);
        land.setSize(size);
        land.setCrop(crop);
        return land;
    }

}
