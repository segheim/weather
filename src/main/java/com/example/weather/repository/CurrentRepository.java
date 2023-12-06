package com.example.weather.repository;

import com.example.weather.dto.CurrentAverageDto;
import com.example.weather.model.Current;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CurrentRepository extends BaseRepository<Current>{

    @Query(
            """
            SELECT c
            FROM Current c
            WHERE c.createAt = (
              SELECT MAX(cur.createAt)
              FROM Current cur
            )
            """
    )
    Optional<Current> findLast();

    @Query(
            """
            SELECT avg(c.temp) as avgTemp, avg(c.wind) as avgWind,
                   avg(c.pressure) as avgPressure, avg(c.humidity) as avgHumidity
            FROM Current c
            WHERE c.createAt BETWEEN :from AND :to
            """
    )
    Optional<CurrentAverageDto> findAllByCreateAtBetween(LocalDateTime from, LocalDateTime to);
}
