package com.illogicalparadox.illogicalParadox.service;

import com.illogicalparadox.illogicalParadox.api.response.WeatherResponse;
import com.illogicalparadox.illogicalParadox.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${Weather.api.key}")
    private String apiKey;
//    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;
    @Autowired
    private RestTemplate restTemplate;
    public WeatherResponse getWeather(String city) {
        String apiTemplate = "http://api.weatherstack.com/current?access_key=apikey&query=<city>";
        if (apiTemplate == null) {
            throw new RuntimeException("Missing 'weather_api' configuration in AppCache");
        }

        String finalAPI = apiTemplate.replace("<city>", city).replace("apikey", apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalAPI, HttpMethod.POST, null, WeatherResponse.class
        );

        return response.getBody();
    }


//    public WeatherResponse getWeather(String city) {
//        String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<city>", city).replace("apikey", apiKey);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
//                finalAPI, HttpMethod.POST, null, WeatherResponse.class
//        );
//        WeatherResponse body=response.getBody();
//        return body;
//    }

}
