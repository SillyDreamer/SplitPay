package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Model.User

class AdapterResult(private var users : ArrayList<User>) : RecyclerView.Adapter<AdapterResult.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = users[position].name
        holder.price.text = (users[position].money.toString().toDouble() / 100).toString()

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(android.R.id.text1)
        var price : TextView = itemView.findViewById(android.R.id.text2)
    }

}