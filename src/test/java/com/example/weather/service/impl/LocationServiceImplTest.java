package com.example.weather.service.impl;

import com.example.weather.model.Location;
import com.example.weather.repository.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;

    @BeforeEach
    public void init() {
            location = new Location("Minsk", "Minsk", "Belarus", 10f, 10f, "Europe/Minsk");
    }

    @Test
    @DisplayName("when correct data then return Location")
    public void saveTest() {
        Location expected = location;
        expected.setId(1l);
        Mockito.when(locationRepository.save(location)).thenReturn(expected);

        Location actual = locationService.save(location);
        Assertions.assertEquals(expected, actual, "Not equals result");
    }
}
