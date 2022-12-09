package mx.edu.utez.dealc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.edu.utez.dealc.databinding.ItemCategoryServiceBinding
import mx.edu.utez.dealc.model.CategoryService

class CategoryServiceAdapter (
    private val eventos: Eventos,
    val context: Context
    ): ListAdapter<CategoryService, CategoryServiceAdapter.ViewHolder>(DiffUtilCallback)
{
    private val ctx = context

    private object DiffUtilCallback : DiffUtil.ItemCallback<CategoryService>() {
        override fun areItemsTheSame(oldItem: CategoryService, newItem: CategoryService): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: CategoryService, newItem: CategoryService): Boolean {
            return true
        }
    }
    interface Eventos {
        fun onItemClick(element: CategoryService, position: Int)
    }

    inner class ViewHolder (private val binding: ItemCategoryServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (element: CategoryService, position: Int) {
            binding.textViewCategoryService.text = element.name

            Glide.with(ctx).load(element.icon).into(binding.iconService)

            binding.iconService.setOnClickListener {
                this@CategoryServiceAdapter.eventos.onItemClick(element, position)
            }

            binding.linearLayoutInfo.setOnClickListener {
                this@CategoryServiceAdapter.eventos.onItemClick(element, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = ItemCategoryServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}