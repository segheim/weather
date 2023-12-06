package com.example.weather.controller;

import com.example.weather.dto.CurrentAverageDto;
import com.example.weather.dto.CurrentDto;
import com.example.weather.model.RequestDates;
import com.example.weather.service.CurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final CurrentService currentService;

    @GetMapping("/current")
    public ResponseEntity<CurrentDto> getCurrentWeather() {
        CurrentDto currentWeather = currentService.findLast();
        return new ResponseEntity<>(currentWeather, HttpStatus.OK);
    }

    @PostMapping("/average")
    public ResponseEntity<CurrentAverageDto> getAverageWeather(@RequestBody RequestDates request) {
        CurrentAverageDto averageWeather = currentService.getAverage(request);
        return new ResponseEntity<>(averageWeather, HttpStatus.OK);
    }
}
