package com.example.weatherinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherinfo.adapter.CitiesListAdapter
import com.example.weatherinfo.data.City
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddCityDialog.CityHandler {

    private lateinit var citiesListAdapter: CitiesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fabAddCity.setOnClickListener {
            showAddCityDialog()
        }

        fabDeleteAll.setOnClickListener {
            citiesListAdapter.deleteAll()
        }

        initRecyclerView()
    }

    private fun showAddCityDialog() {
        AddCityDialog().show(supportFragmentManager, "TAG_CITY_DIALOG")
    }

    private fun initRecyclerView() {
        val cityList = mutableListOf<City>()
        citiesListAdapter = CitiesListAdapter(this@MainActivity, cityList)
        recyclerCitiesList.adapter = citiesListAdapter
    }

    override fun addCity(city: City) {
        citiesListAdapter.addCity(city)
    }
}
