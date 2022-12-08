package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.edu.utez.dealc.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    companion object{
        var enviadoPor = "taxi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}