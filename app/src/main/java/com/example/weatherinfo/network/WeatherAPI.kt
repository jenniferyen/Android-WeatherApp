package com.example.weatherinfo.network

import Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/
// weather?q=Budapest,hu&units=metric&appid=7db67996f7687d449439e8e75cf3c3fe

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Call<Base>
}