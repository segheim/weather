package com.example.weather.service.impl;

import com.example.weather.dto.CurrentAverageDto;
import com.example.weather.dto.CurrentDto;
import com.example.weather.exception.EntityNotFoundException;
import com.example.weather.mapper.CurrentMapper;
import com.example.weather.model.Current;
import com.example.weather.model.Location;
import com.example.weather.model.RequestDates;
import com.example.weather.repository.CurrentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CurrentServiceImplTest {

    @Mock
    private CurrentRepository currentRepository;
    @Mock
    private CurrentMapper mapper;

    @InjectMocks
    private CurrentServiceImpl currentService;

    private Current current;
    private CurrentDto currentDto;
    private CurrentAverageDto currentAverageDto;
    private Location location;
    private Location locationWithId;
    private RequestDates requestDates;
    private LocalDateTime localDateTime = LocalDateTime.of(2023, 10, 20, 00, 00);

    @BeforeEach
    public void init() {
        location = new Location("Minsk", "Minsk", "Belarus", 10f, 10f, "Europe/Minsk");
        current = new Current(10f, 10f, 10f, 10f, null, location, null, "Cloudy");
        currentDto = new CurrentDto(10f, 10f, 10f, 10f, "Cloudy", location, localDateTime);
        locationWithId = location;
        locationWithId.setId(1l);
        requestDates = new RequestDates(localDateTime.toLocalDate(), localDateTime.toLocalDate());
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        currentAverageDto = factory.createProjection(CurrentAverageDto.class);
        currentAverageDto.setAvgTemp(10f);
        currentAverageDto.setAvgWind(10f);
        currentAverageDto.setAvgPressure(10f);
        currentAverageDto.setAvgHumidity(10f);

    }

    @Test
    @DisplayName("when correct data then return Current")
    public void saveTest() {
        Current expected = current;
        expected.setLocation(locationWithId);
        Mockito.when(currentRepository.save(current)).thenReturn(expected);

        Current actual = currentService.save(current);
        Assertions.assertEquals(expected, actual, "Not equals result");
    }

    @Test
    @DisplayName("when repository answer then return Current")
    public void findLastTest() {
        CurrentDto expected = currentDto;
        expected.setLocation(locationWithId);
        current.setLocation(locationWithId);
        Mockito.when(currentRepository.findLast()).thenReturn(Optional.of(current));
        Mockito.when(mapper.toDto(current)).thenReturn(expected);

        CurrentDto actual = currentService.findLast();
        Assertions.assertEquals(expected, actual, "Not equals result");
    }

    @Test
    @DisplayName("when repository answer empty then return Exception")
    public void findLastTest_returnException() {
        Mockito.when(currentRepository.findLast()).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> currentService.findLast());
        Assertions.assertEquals("Last current weather not found", exception.getMessage(), "Messages not equals");
    }

    @Test
    @DisplayName("when repository answer then return CurrentAverageDto")
    public void getAverageTest() {
        CurrentAverageDto expected = currentAverageDto;
        Mockito.when(currentRepository.findAllByCreateAtBetween(localDateTime, localDateTime)).thenReturn(Optional.of(expected));

        CurrentAverageDto actual = currentService.getAverage(requestDates);
        Assertions.assertEquals(expected, actual, "Not equals result");
    }

    @Test
    @DisplayName("when repository answer empty then return Exception")
    public void getAverageTest_returnException() {
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> currentService.findLast());
        Assertions.assertEquals("Last current weather not found", exception.getMessage(), "Messages not equals");
    }

}
