package com.example.weather.service.impl;

import com.example.weather.exception.EntityNotFoundException;
import com.example.weather.model.Current;
import com.example.weather.repository.CurrentRepository;
import com.example.weather.service.CurrentService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class CurrentServiceImpl implements CurrentService {

    private final CurrentRepository currentRepository;

    @Override
    public Current save(Current current) {
        return currentRepository.save(current);
    }

    @Override
    public Current findLast() {
        return currentRepository.findLast().orElseThrow(() ->
                new EntityNotFoundException("Last current weather not found"));
    }
}
