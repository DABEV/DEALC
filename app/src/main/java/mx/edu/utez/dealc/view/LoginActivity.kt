package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.databinding.ActivityLoginBinding
import mx.edu.utez.dealc.viewmodel.FirebaseLoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: FirebaseLoginViewModel
    private val shared = MainCoreApplication.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(FirebaseLoginViewModel::class.java)

        if(shared.isLogged()){
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        binding.buttonAccess.setOnClickListener {

            val user = binding.editTextUsername.text.toString()
            val password = binding.editTextUsername.text.toString()

            lifecycleScope.launch {
                if (!user.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    viewModel.signInWithEmail(
                        binding.editTextUsername.text.toString(),
                        binding.editTextPassword.text.toString(),
                        "Client"
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
        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this, UserRegisterActivity::class.java))
        }
        initObservers()
    }

    fun initObservers() {
        viewModel.resultObj.observe(this) {
            shared.saveEmail(binding.editTextUsername.text.toString(), it!!.id!!)
            startActivity(Intent(applicationContext, SplashActivity::class.java))
        }
        viewModel.errorObj.observe(this) {
            Toast.makeText(
                applicationContext,
                "No se encontro el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}