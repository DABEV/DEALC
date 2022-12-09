package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.databinding.ActivityLoginBinding
import mx.edu.utez.dealc.viewmodel.FirebaseLoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: FirebaseLoginViewModel
    lateinit var shared:Shared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shared = Shared(applicationContext)
        viewModel = ViewModelProvider(this).get(FirebaseLoginViewModel::class.java)

        if(!shared.get().isNullOrEmpty()){
            startActivity(Intent(this, SplashActivity::class.java))
        }

        binding.buttonAccess.setOnClickListener {

            val user = binding.editTextUsername.text.toString()
            val password = binding.editTextUsername.text.toString()

            lifecycleScope.launch {
                if (!user.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    viewModel.signInWithEmail(
                        binding.editTextUsername.text.toString(),
                        binding.editTextPassword.text.toString()
                    )

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Es necesario el correo y/o contraseña para iniciar sesión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        initObservers()

    }

    fun initObservers() {
        viewModel.result.observe(this) {
            shared.guardarUsuario( binding.editTextUsername.text.toString())
            startActivity(Intent(applicationContext, SplashActivity::class.java))
        }
        viewModel.error.observe(this) {
            Toast.makeText(
                applicationContext,
                "No se encontro el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}