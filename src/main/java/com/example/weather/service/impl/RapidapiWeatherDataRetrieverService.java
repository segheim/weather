package com.example.weather.service.impl;

import com.example.weather.exception.EntityNotFoundException;
import com.example.weather.exception.ServiceException;
import com.example.weather.model.Current;
import com.example.weather.model.Location;
import com.example.weather.model.Weather;
import com.example.weather.repository.CurrentRepository;
import com.example.weather.repository.LocationRepository;
import com.example.weather.service.WeatherDataRetrieverService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
@EnableScheduling
public class RapidapiWeatherDataRetrieverService implements WeatherDataRetrieverService {

    private final WebClient webClient;
    private final CurrentRepository currentRepository;
    private final LocationRepository locationRepository;

    @Override
    @Scheduled(cron = "1 * * * * *")
    public void retrieveData() {
        Weather weather = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("weatherapi-com.p.rapidapi.com")
                        .path("/current.json")
                        .queryParam("q", "Minsk")
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.set("X-RapidAPI-Key", "3feeebc156msh11bbe10b811e416p169342jsn0b2e9c5f87a8");
                    httpHeaders.set("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com");
                })
                .retrieve()
                .onStatus(HttpStatus -> HttpStatus.is4xxClientError(),
                        error -> Mono.error(new ServiceException("Rapidapi not found")))
                .onStatus(HttpStatus -> HttpStatus.is5xxServerError(),
                        error -> Mono.error(new ServiceException("Server rapidapi is not responding")))
                .bodyToMono(Weather.class)
                .block();
        log.info(weather.toString());

//        Location location = locationRepository.save(weather.getLocation());
        Current current = weather.getCurrent();
        current.setLocation(weather.getLocation());
        current.setWeatherCondition(weather.getCurrent().getCondition().getText());
        Current currentWeather = currentRepository.save(current);
        log.info(currentWeather.toString());

    }
}
