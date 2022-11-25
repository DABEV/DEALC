package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.dealc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}