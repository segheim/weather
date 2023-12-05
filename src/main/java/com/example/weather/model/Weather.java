package com.example.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Entity
@Data
//@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
public class Weather extends AbstractEntity {

    private Location location;
    private Current current;

}
