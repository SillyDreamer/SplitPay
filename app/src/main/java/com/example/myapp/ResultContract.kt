package com.example.myapp

interface ResultContract {
    interface view {}

    interface presenter {
        fun showUsers() : ArrayList<User>
    }
}