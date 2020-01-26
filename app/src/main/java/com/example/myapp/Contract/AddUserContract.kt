package com.example.myapp.Contract

interface AddUserContract {
    interface Presenter {
        fun addButton(name : String, money : Int)
    }

    interface view {
    }
}