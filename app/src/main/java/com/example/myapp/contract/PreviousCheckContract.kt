package com.example.myapp.contract

interface PreviousCheckContract {

    interface View {}

    interface  Presenter {
        fun showChecks() : ArrayList<Pair<Long, String>>
    }
}