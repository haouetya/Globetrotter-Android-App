package hu.bme.aut.mobweb.byi1sz.globetrotter.network
import hu.bme.aut.mobweb.byi1sz.globetrotter.data.CountryData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface CountryApi {

    @GET("v3.1/name/{name}")fun getCountryByName(@Path("name") name : String):Call<List<CountryData>?>

}