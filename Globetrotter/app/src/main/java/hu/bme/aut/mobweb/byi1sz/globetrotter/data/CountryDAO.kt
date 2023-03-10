package hu.bme.aut.mobweb.byi1sz.globetrotter.data

import androidx.room.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

@Dao
interface CountryDAO {

    @Query("SELECT * FROM countries") fun getAllCountries(): List<CountryData>;
    @Insert fun insertCountry(countryData:CountryData);
    @Delete fun deleteCountry(countryData:CountryData);
    @Update fun updateCountry(countryData:CountryData);


}