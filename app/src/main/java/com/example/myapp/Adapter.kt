package com.example.myapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.model.Product
import com.example.myapp.model.User
import kotlinx.android.synthetic.main.list_item.view.*

class Adapter(private val product : List<Product>,
              private val users : List<User>, val listener: (HashMap<Pair<String, String>, ArrayList<CheckBox>>, List<Product>, Int) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = product.size
    override fun getItemViewType(position: Int): Int {
        return 1
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = product[position].name
        holder.tv2.text = (product[position].price.toDouble() / 100).toString()
        println(checkMap.size)
        holder.add(position)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("ResourceAsColor")
        fun add(position: Int) {
            if (!checkMap.containsKey(Pair(product[position].name, product[position].price))) {
                val arr : ArrayList<CheckBox> = arrayListOf()
                for (x in users) {
                    val cb = CheckBox(itemView.context)
                    cb.text = x.name
                    cb.setTextColor(R.color.primary_darker)
                    itemView.layout.addView(cb)
                    arr.add(cb)
                }
                checkMap.put(Pair(product[position].name, product[position].price), arr)
            }
            else {
                for (x in checkMap.getValue(Pair(product[position].name, product[position].price))) {
                    (x.parent as ViewGroup).removeView(x)
                    x.setTextColor(R.color.primary_darker)
                    itemView.layout.addView(x)
                }
            }
            tv2.setTextColor(R.color.primary_darker)
            tv.setOnClickListener {
                listener(checkMap, product, position)
            }
            tv2.setOnClickListener {
                listener(checkMap, product, position)
            }
        }
        val tv: TextView = itemView.findViewById(R.id.tv)
        val tv2: TextView = itemView.findViewById(R.id.price)
    }
}