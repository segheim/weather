package com.example.weather.service;

import com.example.weather.dto.CurrentAverageDto;
import com.example.weather.dto.CurrentDto;
import com.example.weather.model.Current;
import com.example.weather.model.RequestDates;

public interface CurrentService extends BaseService<Current>{

    CurrentDto findLast();

    CurrentAverageDto getAverage(RequestDates request);

}
