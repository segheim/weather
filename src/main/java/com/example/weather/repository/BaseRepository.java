package com.example.weather.repository;

import com.example.weather.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
