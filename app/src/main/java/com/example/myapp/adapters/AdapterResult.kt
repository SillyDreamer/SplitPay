package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.model.User

class AdapterResult(private var users : ArrayList<User>, val listener:(Int) -> Unit, val listenerButton:(Int) -> Unit) : RecyclerView.Adapter<AdapterResult.ViewHolder>() {
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
        var price2 : TextView = itemView.findViewById(R.id.tv)
        var price3 : TextView = itemView.findViewById(R.id.tv2)
        var share : Button = itemView.findViewById(R.id.share_button)
        var edit : Button = itemView.findViewById(R.id.edit_button)

        fun bind(pos : Int) {
            name.text = users[pos].name
            price.text = "Итого:  " + (users[pos].money.toString().toDouble() / 100).toString()
            price3.text = "Осталось заплатить:  " + (users[pos].money.toString().toDouble() / 100).toString()
            price2.text = "Заплачено:  0.00"
            share.setOnClickListener {
                listener(pos)
            }
            edit.setOnClickListener {
                listenerButton(pos)
            }

        }
    }

}