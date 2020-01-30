package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Model.User

class AdapterResult(private var users : ArrayList<User>, val listener:(Int) -> Unit) : RecyclerView.Adapter<AdapterResult.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_result, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.name_result)
        var price : TextView = itemView.findViewById(R.id.money_result)
        var share : Button = itemView.findViewById(R.id.share_button)

        fun bind(pos : Int) {
            name.text = users[pos].name
            price.text = (users[pos].money.toString().toDouble() / 100).toString()
            share.setOnClickListener {
                listener(pos)
            }

        }
    }

}