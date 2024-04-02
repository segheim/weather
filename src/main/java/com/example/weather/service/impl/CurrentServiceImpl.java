package com.example.weather.service.impl;

import com.example.weather.dto.CurrentAverageDto;
import com.example.weather.dto.CurrentDto;
import com.example.weather.exception.EntityNotFoundException;
import com.example.weather.mapper.CurrentMapper;
import com.example.weather.model.Current;
import com.example.weather.model.RequestDates;
import com.example.weather.repository.CurrentRepository;
import com.example.weather.service.CurrentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@Data
public class CurrentServiceImpl implements CurrentService {

    private final CurrentRepository currentRepository;
    private final CurrentMapper mapper;

    @Override
    public Current save(Current current) {
        return currentRepository.save(current);
    }

    @Override
    public CurrentDto findLast() {
        Current currentWeather = currentRepository.findLast().orElseThrow(() ->
                new EntityNotFoundException("Last current weather not found"));
        log.info("Current weather data {}", currentWeather.toString());
        return mapper.toDto(currentWeather);
    }

    @Override
    @Transactional
    public CurrentAverageDto getAverage(RequestDates request) {
        LocalDateTime from = LocalDateTime.of(request.getFrom(), LocalTime.MIDNIGHT);
        LocalDateTime to = LocalDateTime.of(request.getTo(), LocalTime.MIDNIGHT);
        CurrentAverageDto currentAverageDto = currentRepository.findAllByCreateAtBetween(from, to).orElseThrow(() ->
                new EntityNotFoundException("Last current weather not found"));
        log.info("Weather average data {}", currentAverageDto.toString());
        return currentAverageDto;
    }
}
