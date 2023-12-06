package com.example.weather.service.impl;

import com.example.weather.exception.ServiceException;
import com.example.weather.model.Current;
import com.example.weather.model.Weather;
import com.example.weather.repository.CurrentRepository;
import com.example.weather.repository.LocationRepository;
import com.example.weather.service.WeatherDataRetrieverService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    public static final String SCHEME_HTTPS = "https";
    public static final String PATH = "/current.json";
    public static final String QUERY_PARAM_NAME = "q";
    public static final String HEADER_RAPID_API_KEY = "X-RapidAPI-Key";
    public static final String HEADER_RAPID_API_HOST = "X-RapidAPI-Host";

    @Value("${location}")
    private String parameterLocation;
    @Value("${rapidapi.key}")
    private String rapidApiKey;
    @Value("${rapidapi.host}")
    private String rapidApiHost;

    private final WebClient webClient;
    private final CurrentRepository currentRepository;
    private final LocationRepository locationRepository;

    @Override
    @Scheduled(cron = "0 0/30 * * * *")
    @Value("${key.name}")
    public void retrieveData() {
        Weather weather = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(SCHEME_HTTPS)
                        .host(rapidApiHost)
                        .path(PATH)
                        .queryParam(QUERY_PARAM_NAME, parameterLocation)
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.set(HEADER_RAPID_API_KEY, rapidApiKey);
                    httpHeaders.set(HEADER_RAPID_API_HOST, rapidApiHost);
                })
                .retrieve()
                .onStatus(HttpStatus -> HttpStatus.is4xxClientError(),
                        error -> Mono.error(new ServiceException("Rapidapi not found")))
                .onStatus(HttpStatus -> HttpStatus.is5xxServerError(),
                        error -> Mono.error(new ServiceException("Server rapidapi is not responding")))
                .bodyToMono(Weather.class)
                .block();
        log.info("Data from rapidapi.com: {}", weather.toString());

        Current current = weather.getCurrent();
        current.setLocation(weather.getLocation());
        current.setWeatherCondition(weather.getCurrent().getCondition().getText());
        Current currentWeather = currentRepository.save(current);
        log.info("Saved in repository: {}", currentWeather);
    }
}
