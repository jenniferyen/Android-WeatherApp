package com.example.weatherinfo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.weatherinfo.data.City
import kotlinx.android.synthetic.main.add_city_dialog.view.*

class AddCityDialog : DialogFragment() {

    interface CityHandler {
        fun addCity(city: City)
    }

    private lateinit var cityHandler: CityHandler
    private lateinit var etCity: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException("The Activity does not implement the CityHandler interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.new_city))

        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.add_city_dialog, null
        )
        etCity = dialogView.etCity

        builder.setView(dialogView)
        builder.setPositiveButton("OK") { _, _ ->
        }

        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCity.text.isNotEmpty()) {
                handleAddCity()
                (dialog as AlertDialog).dismiss()
            } else {
                etCity.error = getString(R.string.empty_field)
            }
        }
    }

    private fun handleAddCity() {
        cityHandler.addCity(
            City(null, etCity.text.toString(), "", "")
        )
    }

}