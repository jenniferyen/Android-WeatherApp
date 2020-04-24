package com.example.weatherinfo

import Base
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherinfo.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    override fun onResume() {
        super.onResume()
        btnBack.setOnClickListener {
            val intentBack = Intent()
            intentBack.setClass(this, MainActivity::class.java)
            startActivity(intentBack)
            finish()
        }
        val name = intent.getStringExtra("KEY_DATA")
        tvName.text = name
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherAPI = retrofit.create(WeatherAPI::class.java)
        val call = weatherAPI.getWeather(
            tvName.text.toString(),
            "metric",
            "7db67996f7687d449439e8e75cf3c3fe"
        )
        call.enqueue(
            object : Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {
                    Log.d("reached ", "onFailure")
                    tvName.text = getString(R.string.API_error)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    Log.d("reached ", "onResponse")
                    val weatherBase: Base? = response.body()
                    tvTemp.text = "Temperature: ${weatherBase?.main?.temp}°C"
                    tvDesc.text = "Description: ${weatherBase?.weather?.get(0)?.description}"
                    tvMinTemp.text = "Min temp: ${weatherBase?.main?.temp_min}"
                    tvMaxTemp.text = "Max temp: ${weatherBase?.main?.temp_max}"
                    tvHumidity.text = "Humidity: ${weatherBase?.main?.humidity}%"
                    tvPressure.text = "Pressure: ${weatherBase?.main?.pressure}hPa"
                    Glide.with(this@DetailsActivity).load(
                        ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png")
                    )
                        .into(ivIcon)
                }
            }
        )
    }
}
