package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterPreviousCheck(private var list : ArrayList<Pair<Long, String>>, val listener:(Long) -> Unit) : RecyclerView.Adapter<AdapterPreviousCheck.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.findViewById(android.R.id.text1)
        var date : TextView = itemView.findViewById(android.R.id.text2)
        fun bind(position: Int) {
            id.text = list[position].first.toString()
            id.setOnClickListener {
                listener(list[position].first)
            }
            date.text = list[position].second
            date.setOnClickListener {
                listener(list[position].first)
            }
        }
    }

}