package com.example.weather.service;

import com.example.weather.model.Current;

public interface CurrentService extends BaseService<Current>{

    Current findLast();

}
