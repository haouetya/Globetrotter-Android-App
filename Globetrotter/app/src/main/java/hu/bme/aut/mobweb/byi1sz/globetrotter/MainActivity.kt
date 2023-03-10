package hu.bme.aut.mobweb.byi1sz.globetrotter

import android.R
import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.byi1sz.globetrotter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    companion object {
        var KEY_NAME="KEY_NAME"
        var KEY_PASSWORD="KEY_PASSWORD"
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCancel.setOnClickListener {
            binding.etPassword.text.clear()
            binding.etUserName.text.clear()
        }

        binding.btnLogin.setOnClickListener {
            if(TextUtils.isEmpty(binding.etUserName.text)){
                binding.etUserName.error = "Cannot be empty"
            }
            else {
                val intent = Intent(this, CountryListActivity::class.java)
                intent.putExtra(KEY_NAME, binding.etUserName.text.toString())
                intent.putExtra(KEY_PASSWORD, binding.etPassword.text.toString())
                startActivity(intent)
            }

        }

    }


}