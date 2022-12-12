package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.databinding.ActivityMenuBinding
import mx.edu.utez.dealc.viewmodel.FirebaseLoginViewModel

class MenuActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuBinding
    private val shared = MainCoreApplication.shared
    lateinit var viewModel: FirebaseLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FirebaseLoginViewModel::class.java)

        getFirebaseToken()

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnHistory.setOnClickListener {
            startActivity(Intent(this, ServiceHistoryActivity::class.java))
        }

        binding.btnService.setOnClickListener {
            startActivity(Intent(this, CategoryServiceActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
            }
        }

        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        initObservers()
    }

    private fun initObservers() {
        viewModel.result.observe(this) {
            shared.delete()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

        viewModel.error.observe(this) {
            Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFirebaseToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            var TAG = "FB:"
            if (it.isSuccessful){
                var token = it.result
                println("$TAG $token")
            }else {
                println("$TAG Error ${it.exception?.message}")
            }
        }
    }
}