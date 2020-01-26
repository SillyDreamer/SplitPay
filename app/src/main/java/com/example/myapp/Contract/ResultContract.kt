package com.example.myapp.Contract

import com.example.myapp.Model.User

interface ResultContract {
    interface view {}

    interface presenter {
        fun showUsers() : ArrayList<User>
    }
}