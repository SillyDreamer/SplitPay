package com.example.myapp.contract

import com.example.myapp.model.User

interface ResultContract {
    interface view {}

    interface presenter {
        fun showUsers() : ArrayList<User>
    }
}