package com.illogicalparadox.illogicalParadox.api.response;
import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Setter
@Getter
public class WeatherResponse{

    private Current current;


    @Setter
    @Getter
    public class Current{

        private int temperature;
        private int weather_code;

        private int wind_speed;
        private int wind_degree;
        private String wind_dir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;
        private int uv_index;
        private int visibility;
        private String is_day;
    }


}

