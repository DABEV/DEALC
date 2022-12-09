package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.dealc.databinding.ActivityDetailServiceBinding

class DetailServiceActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageViewChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }
}