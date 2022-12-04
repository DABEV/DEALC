package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.databinding.ActivityUserRegisterBinding
import mx.edu.utez.dealc.model.Client
import mx.edu.utez.dealc.viewmodel.ClientViewModel
import mx.edu.utez.dealc.viewmodel.FirebaseLoginViewModel

class UserRegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserRegisterBinding
    lateinit var viewModel: ClientViewModel
    lateinit var viewModelFirebase: FirebaseLoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)
        viewModelFirebase = ViewModelProvider(this).get(FirebaseLoginViewModel::class.java)


        binding.buttonRegisterUser.setOnClickListener {

            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()

           lifecycleScope.launch{
             if(!name.isNullOrEmpty() && !lastName.isNullOrEmpty() && !email.isNullOrEmpty()
                 && !phone.isNullOrEmpty() && !password.isNullOrEmpty()){
                 viewModel.addClient(Client(null,name, lastName, email,phone))

                 viewModelFirebase.createUserWithEmailAndPassword(
                     binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
                 )
             }else{
                 Toast.makeText(applicationContext, "Ocurrio un error revise los datos", Toast.LENGTH_SHORT).show()
             }
           }

        }

    }


    fun initObservers(){
    }
}