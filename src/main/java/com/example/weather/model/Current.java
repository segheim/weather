package com.example.weather.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "weathers")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current extends AbstractEntity{

    @Column(name = "temp", nullable = false)
    @JsonProperty("temp_c")
    private Float temp;
    @Column(name = "wind", nullable = false)
    @JsonProperty("wind_kph")
    private Float wind;
    @Column(name = "pressure", nullable = false)
    @JsonProperty("pressure_mb")
    private Float pressure;
    @Column(name = "humidity", nullable = false)
    @JsonProperty("humidity")
    private Float humidity;
    @Column(name = "create_at", insertable = false)
    private LocalDateTime createAt;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Transient
    private Condition condition;
    @Column(name = "condition")
    private String weatherCondition;


}
