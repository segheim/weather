package com.example.weather.dto;

import com.example.weather.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentDto {

    private Float temp;
    private Float wind;
    private Float pressure;
    private Float humidity;
    private String weatherCondition;
    private Location location;
    private LocalDateTime createAt;
}
