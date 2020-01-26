package com.example.myapp

interface AddUserContract {
    interface Presenter {
        fun addButton(name : String, money : Int)
    }

    interface view {
    }
}