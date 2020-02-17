package com.example.myapp.contract

import com.example.myapp.model.User
import com.example.myapp.view.ResultActivity

interface ResultContract {
    interface view {
        fun showUsers(array: ArrayList<User>)
    }

    interface presenter {
        fun showUsers(check_id: Long)
        fun updateUser(name: String, money: Int, paid: Int, id: Long, check_id: Long)
        fun attachView(view: ResultActivity)
        fun detachView()
    }
}