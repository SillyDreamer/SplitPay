package com.example.myapp.contract

import android.widget.CheckBox
import com.example.myapp.model.Product
import com.example.myapp.model.User

interface MainContract {

    interface view {

    }

    interface presenter {
        fun showProducts() : ArrayList<Product>
        fun showUsers() : ArrayList<User>
        fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>)
        fun addOneMoreCheck(qrResult: String?)
    }
}