package com.example.myapp.contract

import com.example.myapp.view.AddUserActivity

interface AddUserContract {
    interface Presenter {
        fun addButton(name: String, money: Int)
        fun lastCheckId()
        fun attachView(view: AddUserActivity)
        fun detachView()
    }

    interface view {
        fun showCheckId(id: Long)
    }
}