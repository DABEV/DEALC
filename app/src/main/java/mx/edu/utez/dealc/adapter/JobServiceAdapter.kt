package mx.edu.utez.dealc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.edu.utez.dealc.databinding.ItemServiceJobBinding
import mx.edu.utez.dealc.model.Job

class JobServiceAdapter(
    private val eventos: Eventos,
    val context: Context
): ListAdapter<Job, JobServiceAdapter.ViewHolder>(DiffUtilCallback) {
    private val ctx = context

    private object DiffUtilCallback : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return true
        }
    }
    interface Eventos {
        fun onItemClick(element: Job, position: Int)
    }

    inner class ViewHolder (private val binding: ItemServiceJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (element: Job, position: Int) {
            binding.textViewJob.text = element.name

            Glide.with(ctx).load(element.icon).into(binding.imageViewJob)

            binding.imageViewJob.setOnClickListener {
                this@JobServiceAdapter.eventos.onItemClick(element, position)
            }

            binding.linearLayoutInfo.setOnClickListener {
                this@JobServiceAdapter.eventos.onItemClick(element, position)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = ItemServiceJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}