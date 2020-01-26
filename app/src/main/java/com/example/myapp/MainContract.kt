package com.example.myapp

import android.widget.CheckBox

interface MainContract {

    interface view {

    }

    interface presenter {
        fun showProducts() : ArrayList<Product>
        fun showUsers() : ArrayList<User>
        fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>)
    }
}