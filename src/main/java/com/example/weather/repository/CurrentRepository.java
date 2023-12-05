package com.example.weather.repository;

import com.example.weather.model.Current;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CurrentRepository extends BaseRepository<Current>{

    @Query(nativeQuery = true, value =
            """
            SELECT id, temp, wind, pressure, humidity, create_at
            FROM weathers
            WHERE create_at = (
              SELECT MAX(create_at)
              FROM weathers
            )
            """
    )
    Optional<Current> findLast();
}
