package hu.bme.aut.mobweb.byi1sz.globetrotter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.byi1sz.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryData
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.byi1sz.globetrotter.databinding.CountryItemBinding

    class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder>  {

    private var countryItems = mutableListOf<CountryData>()
    val context: Context
    private lateinit var binding: CountryItemBinding


        inner class ViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

        constructor(context: Context, dbCountries: List<CountryData>) {
            this.context = context
            this.countryItems.addAll(dbCountries)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return countryItems.size
    }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentCountry = countryItems[position]
            holder.binding.tvCountryName.text = currentCountry.name.common
            holder.binding.tvCountryCode.text = currentCountry.cca3
            Glide
                .with(context)
                .load(currentCountry.flags.png)
                .centerCrop()
                .into(holder.binding.ivFlag);
            holder.binding.btnDelete.setOnClickListener {
                deleteCountry(holder.adapterPosition)
            }
        }

    private fun deleteCountry(position: Int) {

        val t1 = Thread {
            CountryDatabase.getInstance(context).countryDao().deleteCountry(countryItems[position])
            (context as CountryListActivity).runOnUiThread {
                countryItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()

    }

    public fun addCountry(country: CountryData) {
        val t2 = Thread {
            try {
                CountryDatabase.getInstance(context).countryDao().insertCountry(country)
                (context as CountryListActivity).runOnUiThread {
                    countryItems.add(country)
                    notifyItemInserted(countryItems.lastIndex)
                }
            } catch (e: Exception) {
                Snackbar.make( (context as CountryListActivity).binding.root,e.localizedMessage, Snackbar.LENGTH_LONG).show()
            }
        }.start()
    }
    }

