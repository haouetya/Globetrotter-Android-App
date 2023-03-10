package hu.bme.aut.mobweb.byi1sz.globetrotter.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.mobweb.byi1sz.globetrotter.MainActivity
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryData
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.Flags
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.Name
import hu.bme.aut.mobweb.byi1sz.globetrotter.databinding.ActivityMainBinding
import hu.bme.aut.mobweb.byi1sz.globetrotter.databinding.DialogCountryNameBinding


class CountryDialog : DialogFragment() {

    interface CountryHandler {
        fun countryAdded(country : String)
    }

    lateinit var countryHandler: CountryHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CountryHandler){
             countryHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the CountryHandler interface.")
        }
    }

    private lateinit var etCountryName: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Country dialog")
        val binding = DialogCountryNameBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        etCountryName = binding.etCountryName
        builder.setPositiveButton("Ok") {
                dialog, which ->
        }
        builder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return builder.create()

    }

    override fun onResume() {
        super.onResume()
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etCountryName.text.isNotEmpty()) {
                countryHandler.countryAdded(etCountryName.text.toString())

            } else {
                etCountryName.error = "This field can not be empty"
            }
        }
    }
}