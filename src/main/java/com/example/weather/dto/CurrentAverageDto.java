package com.example.weather.dto;

public interface CurrentAverageDto {

    Float getAvgTemp();

    Float getAvgWind();

    Float getAvgPressure();

    Float getAvgHumidity();

    void setAvgTemp(Float temp);

    void setAvgWind(Float wind);

    void setAvgPressure(Float pressure);

    void setAvgHumidity(Float humidity);

}
