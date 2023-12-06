package com.example.weather.service.impl;

import com.example.weather.model.Location;
import com.example.weather.repository.LocationRepository;
import com.example.weather.service.LocationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Data
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        log.info("Location for save {}", location.toString());
        return locationRepository.save(location);
    }
}
