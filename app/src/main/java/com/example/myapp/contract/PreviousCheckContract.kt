package com.example.myapp.contract

import com.example.myapp.view.PreviousCheckActivity

interface PreviousCheckContract {

    interface View {
        fun showChecks(array: ArrayList<Triple<String, String, String>>)
    }

    interface Presenter {
        fun showChecks()
        fun updateCheck(name: String, date: String, id: Long)
        fun attachView(view: PreviousCheckActivity)
        fun detachView()
    }
}