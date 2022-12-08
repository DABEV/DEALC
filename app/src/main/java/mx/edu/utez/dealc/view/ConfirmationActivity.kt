package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.dealc.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {
    lateinit var binding : ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequire.setOnClickListener {  }
    }
}