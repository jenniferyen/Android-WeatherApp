package com.example.weatherinfo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherinfo.DetailsActivity
import com.example.weatherinfo.R
import com.example.weatherinfo.data.City
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesListAdapter(
    private val context: Context,
    cities: List<City>
) : RecyclerView.Adapter<CitiesListAdapter.ViewHolder>() {

    private var citiesList = mutableListOf<City>()

    init {
        citiesList.addAll(cities)
    }

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemRow = LayoutInflater.from(context).inflate(
            R.layout.city_item, parent, false
        )
        return ViewHolder(itemRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = citiesList[position]

        holder.tvName.text = city.name

        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }
        holder.itemView.setOnClickListener {
            val intentDetails = Intent()
            intentDetails.setClass(context, DetailsActivity::class.java)
            intentDetails.putExtra(KEY_DATA, city.name)

            context.startActivity(intentDetails)
        }
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    fun addCity(city: City) {
        citiesList.add(city)
        notifyItemInserted(citiesList.lastIndex)
    }

    private fun deleteItem(index: Int) {
        citiesList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun deleteAll() {
        citiesList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val btnDelete: Button = itemView.btnDelete
    }
}