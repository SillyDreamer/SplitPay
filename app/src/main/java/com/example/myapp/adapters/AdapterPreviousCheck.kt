package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class AdapterPreviousCheck(private var list : ArrayList<Triple<String, String, String>>, val listener:(Long) -> Unit, val listener2:(Int) -> Unit) : RecyclerView.Adapter<AdapterPreviousCheck.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_previous_check, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.findViewById(R.id.text)
        var date : TextView = itemView.findViewById(R.id.text2)
        var but : Button = itemView.findViewById(R.id.edit_button)
        fun bind(position: Int) {
            id.text = list[position].second
            id.setOnClickListener {
                listener(list[position].first.toLong())
            }
            date.text = list[position].third
            date.setOnClickListener {
                listener(list[position].first.toLong())
            }

            but.setOnClickListener {
                listener2(position)
            }
        }
    }

}