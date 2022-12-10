package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.databinding.ActivityChatBinding
import mx.edu.utez.dealc.model.ChatRequest
import mx.edu.utez.dealc.model.Client
import mx.edu.utez.dealc.model.Message
import mx.edu.utez.dealc.viewmodel.ServiceProviderRequestViewModel
import java.util.*
import kotlin.math.log

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    lateinit var viewModel: ServiceProviderRequestViewModel
    lateinit var adapter: ChatAdapter
    lateinit var serviceId: String
    private var messages: MutableList<Message> = mutableListOf()
    private val shared = MainCoreApplication.shared

    companion object{
        var enviadoPor = MainCoreApplication.shared.getId()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("CHAT: onload enviadoPor ${enviadoPor}")
        serviceId = intent.getStringExtra("serviceId")!!
        viewModel = ViewModelProvider(this).get(ServiceProviderRequestViewModel::class.java)
        setData()
        binding.enviar.setOnClickListener {
            if(!binding.msgEnviar.text.toString().isNullOrEmpty()){
                lifecycleScope.launch {
                    messages.add(Message(shared.getId(),
                        shared.get(), binding.msgEnviar.text.toString()
                    ))
                    viewModel.addToChat(
                        intent.getStringExtra("serviceId")!!,
                        messages
                    )
                }
                addItem(Message(shared.getId(),
                    shared.get(), binding.msgEnviar.text.toString()
                ))
            }else{
                Toast.makeText(this, "Mensaje requerido", Toast.LENGTH_SHORT).show()
            }
        }

        renderChat()
        initObservers()
    }

    fun renderChat(){
        FirebaseDatabase.getInstance()
            .getReference("ChatRequest")
            .child("chat${serviceId}")
            .limitToLast(1)
            .addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var userId = snapshot.child("userId").value.toString()
                    var content = snapshot.child("content").value.toString()
                    var username = snapshot.child("username").value.toString()
                    println("CHAT: onChildAdded enviado ${enviadoPor}")
                    println("CHAT: onChildAdded usuario ${userId}")
                    if (!userId.equals(enviadoPor)){
                        addItem(Message(userId,content,username))
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }


    fun initObservers() {
        viewModel.responsesToSend["addToChat"]?.first?.observe(this) {
            binding.msgEnviar.setText("")
        }
        viewModel.responsesToSend["addToChat"]?.second?.observe(this) {
            Toast.makeText(applicationContext, "No se ha enviado el mensaje", Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.responsesToSend["getMessagesFromChat"]?.first?.observe(this) {
            messages = it as MutableList<Message>
            println("CHAT: initObservers messages ${messages.size}")
            println("CHAT: initObservers messages ${messages.isEmpty()}")

            if(!messages.isEmpty()){
                println("CHAT: initObservers before")
                println("CHAT: initObservers ${messages}")
                messages.forEach{
                    println("CHAT: initObservers after")
                    println("CHAT: foreah consultar ${it}")
                    addItem(it)
                }
            }
        }
        viewModel.responsesToSend["getMessagesFromChat"]?.second?.observe(this) {
            Toast.makeText(applicationContext, "No existen mensajes en el chat", Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun setData() {
        adapter = ChatAdapter(this)
        binding.lv.adapter = adapter
        adapter.notifyDataSetChanged()
        consultarDatos()
    }

    fun consultarDatos(){
        lifecycleScope.launch {
            viewModel.getMessagesFromChat(intent.getStringExtra("serviceId")!!)
        }
    }

    fun addItem(data: Message){
        adapter.add(data)
        adapter.notifyDataSetChanged()
        binding.lv.setSelection(adapter.count-1)
    }
}