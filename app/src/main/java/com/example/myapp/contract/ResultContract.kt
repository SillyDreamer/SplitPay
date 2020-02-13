package com.example.myapp.contract

interface ResultContract {
    interface view {}

    interface presenter {
        fun showUsers(check_id : Long)
    }
}