package com.example.weather.service.impl;

import com.example.weather.model.Location;
import com.example.weather.repository.LocationRepository;
import com.example.weather.service.LocationService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
