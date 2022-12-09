package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.utez.dealc.adapter.CategoryServiceAdapter
import mx.edu.utez.dealc.adapter.ServiceProviderRequestAdapter
import mx.edu.utez.dealc.databinding.ActivityCategoryServiceBinding
import mx.edu.utez.dealc.model.CategoryService
import mx.edu.utez.dealc.model.ServiceProviderRequest

class CategoryServiceActivity : AppCompatActivity(), CategoryServiceAdapter.Eventos {
    lateinit var binding: ActivityCategoryServiceBinding
    lateinit var adapter: CategoryServiceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setData()
    }

    private fun setData () {

        val list = ArrayList<CategoryService>()

        Firebase.firestore.collection("CategoryService")
            .get()
            .addOnSuccessListener { docs ->

                if(docs != null){
                    for (doc in docs){
                        list.add(CategoryService(doc.id, doc.data["name"].toString(),
                        ""))

                    }
                    binding.recyclerViewCategoryService.layoutManager = LinearLayoutManager(this)
                    adapter = CategoryServiceAdapter(this, this)
                    binding.recyclerViewCategoryService.adapter = adapter
                    adapter.submitList(list)
                    adapter.notifyDataSetChanged()
                }else {
                    Toast.makeText(applicationContext, "No such document", Toast.LENGTH_SHORT).show()
                }

            }



    }


    override fun onItemClick(element: CategoryService, position: Int) {
    }
}