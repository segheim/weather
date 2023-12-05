package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "region", nullable = false)
    private String region;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "lat", nullable = false)
    private Float lat;
    @Column(name = "lon", nullable = false)
    private Float lon;
    @Column(name = "time_zone", nullable = false)
    @JsonProperty("tz_id")
    private String timeZone;

}
