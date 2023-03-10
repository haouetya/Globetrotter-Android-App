package hu.bme.aut.mobweb.byi1sz.globetrotter.data

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName="countries")class CountryData(var name : Name, @PrimaryKey var cca3 : String, var flags : Flags) {
}

class Name (var common : String) {
}

class Flags (var png : String) {
}

class Converters{
    private val gson = Gson();

    @TypeConverter
    fun nameToString(name: Name) :String{
        return gson.toJson(name)
    }

    @TypeConverter
    fun stringToName(stringName: String) : Name{
        return gson.fromJson(stringName,Name::class. java)
    }
    @TypeConverter
    fun flagsToString(flagsToString: Flags) :String{
        return gson.toJson(flagsToString)
    }
    @SuppressLint("RestrictedApi")
    @TypeConverter
    fun stringToFlags(stringFlags: String) : Flags{
        return gson.fromJson(stringFlags, Flags::class.java)
    }
}