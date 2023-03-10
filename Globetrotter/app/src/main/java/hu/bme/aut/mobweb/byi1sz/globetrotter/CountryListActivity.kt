package hu.bme.aut.mobweb.byi1sz.globetrotter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.byi1sz.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryData
import hu.bme.aut.mobweb.byi1sz.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.byi1sz.globetrotter.fragment.CountryDialog
import android.content.Context
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.byi1sz.globetrotter.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

    lateinit var binding: ActivityCountryListBinding
    private lateinit var countryAdapter: CountryAdapter

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val name = intent.getStringExtra("KEY_NAME")

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
            Snackbar.make(binding.root,name.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        binding.fab.setOnClickListener { view ->
            CountryDialog().show(supportFragmentManager, "Dialog")

            val t = Thread {
                val dbCountries =  CountryDatabase.getInstance(this).countryDao().getAllCountries()
                this.runOnUiThread {
                    countryAdapter = CountryAdapter(this, dbCountries)
                    binding.recyclerCountry.adapter = countryAdapter
                }
            }.start()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_country_list,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.map -> {
                Snackbar.make(binding.root,"Map", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                true
            }
            R.id.about ->{
                Snackbar.make(binding.root,getString(R.string.yassine_haouet_byi1sz_2022), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                return true
            }
            R.id.logout ->{
                finish();
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        Snackbar.make(findViewById(R.id.toolbar),"Press Logout", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun countryAdded(country: String) {
        NetworkManager. getCountryByName(country) .enqueue(object : Callback<List<CountryData>?> {
            override fun onResponse(
                call: Call<List<CountryData>?>,
                response: Response<List<CountryData>?>

            ){
                if(response.isSuccessful) {
                    countryAdapter.addCountry(response.body()!![0])
                } else {
                    Snackbar.make(binding.root, "Error: " + response.message(),
                    Snackbar .LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {
                t.printStackTrace()
                Snackbar.make(binding.root, "Network request error occurred, check LOG",
                Snackbar.LENGTH_LONG).show()
            }
        })
    }


}


