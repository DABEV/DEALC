package mx.edu.utez.dealc.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import mx.edu.utez.dealc.R

class ChatAdapter(context: Context): BaseAdapter() {
    var messages = ArrayList<ChatModel>()
    var ctx = context

    fun add(data: ChatModel) {
        messages.add(data)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(p0: Int): Any {
        return messages[0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var holder = MessageViewHolder()
        var myView = view
        var messageInflater = LayoutInflater.from(ctx)
        var message = messages.get(position).msg
        if (messages.get(position).user.equals(ChatActivity.enviadoPor)) {
            myView = messageInflater.inflate(R.layout.my_message, null)
            holder.bodyMessage = myView.findViewById(R.id.mensaje)
            holder.bodyMessage?.setText(message)
        } else {
            myView = messageInflater.inflate(R.layout.other_message, null)
            holder.bodyMessage = myView.findViewById(R.id.mensaje)
            holder.bodyMessage?.setText(message)
        }
        return myView
    }
}

internal class MessageViewHolder {
    var bodyMessage: TextView? = null
}