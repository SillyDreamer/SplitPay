package com.example.myapp.contract

interface AddUserContract {
    interface Presenter {
        fun addButton(name : String, money : Int)
    }

    interface view {
    }
}