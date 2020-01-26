package com.example.myapp.Contract

import android.widget.CheckBox
import com.example.myapp.Model.Product
import com.example.myapp.Model.User

interface MainContract {

    interface view {

    }

    interface presenter {
        fun showProducts() : ArrayList<Product>
        fun showUsers() : ArrayList<User>
        fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>)
    }
}