package hu.bme.aut.mobweb.byi1sz.globetrotter.network

import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object NetworkManager {
    lateinit var countryApi : CountryApi;
    lateinit var retrofit : Retrofit
    private const val SERVICE_URL="https://restcountries.com/"
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL).client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countryApi = retrofit.create(CountryApi::class.java)
    }
    fun getCountryByName( name: String): Call<List<CountryData>?> {
        return countryApi.getCountryByName(name)
    }
}