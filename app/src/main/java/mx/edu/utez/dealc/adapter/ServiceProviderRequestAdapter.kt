package mx.edu.utez.dealc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.edu.utez.dealc.databinding.ItemServiceHistoryBinding
import mx.edu.utez.dealc.model.ServiceProviderRequest

class ServiceProviderRequestAdapter(
    private val eventos: Eventos,
    val context: Context
) : ListAdapter<ServiceProviderRequest, ServiceProviderRequestAdapter.ViewHolder>(DiffUtilCallback)
{
    private val ctx = context

    private object DiffUtilCallback : DiffUtil.ItemCallback<ServiceProviderRequest>() {
        override fun areItemsTheSame(oldItem: ServiceProviderRequest, newItem: ServiceProviderRequest): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: ServiceProviderRequest, newItem: ServiceProviderRequest): Boolean {
            return true
        }
    }

    interface Eventos {
        fun onItemClick(element: ServiceProviderRequest, position: Int)
        fun onChatClick(element: ServiceProviderRequest)
        fun onAcceptClick(element: ServiceProviderRequest)
        fun onRejectClick(element: ServiceProviderRequest)
    }

    inner class ViewHolder (private val binding: ItemServiceHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (element: ServiceProviderRequest, position: Int) {
            binding.textViewShortDescription.text = element.shortDescription

            binding.linearLayoutInfo.setOnClickListener {
                this@ServiceProviderRequestAdapter.eventos.onItemClick(element, position)
            }

            binding.imageViewChat.setOnClickListener {
                this@ServiceProviderRequestAdapter.eventos.onChatClick(element)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = ItemServiceHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}